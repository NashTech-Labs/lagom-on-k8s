package com.knoldus.lagom.sample.restaurant.menu.impl;

import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.knoldus.lagom.sample.restaurant.menu.api.MenuService;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Stepdefs {
    private static final long TIMEOUT = 10L;

    private static ServiceTest.TestServer server;
    private static MenuService service;
    private static List<String> menu;

    @Before
    public static void setUp() {
        server = ServiceTest.startServer(ServiceTest.defaultSetup().withCassandra());
        service = server.client(MenuServiceImpl.class);
    }

    @Given("Restaurant has Menu")
    public void restaurant_has_Menu() throws InterruptedException, ExecutionException, TimeoutException {
        // Write code here that turns the phrase above into concrete actions
        service.addItem().invoke(Item.builder().name("item").build()).toCompletableFuture()
                .get(TIMEOUT, TimeUnit.SECONDS);
        //Waiting for data to get added in DB.
        Thread.sleep(5000L);
    }

    @When("User asks for Menu")
    public void user_asks_for_Menu() throws InterruptedException, ExecutionException, TimeoutException {
        // Write code here that turns the phrase above into concrete actions
        menu = service.getMenu().invoke().toCompletableFuture().get(TIMEOUT, TimeUnit.SECONDS);
    }

    @Then("User should Get Menu")
    public void user_should_Get_Menu() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(menu.contains("item"));
    }

    @After
    public static void afterClass() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

}
