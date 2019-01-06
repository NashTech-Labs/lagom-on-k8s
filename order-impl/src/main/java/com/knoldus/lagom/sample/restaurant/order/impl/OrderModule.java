package com.knoldus.lagom.sample.restaurant.order.impl;

import akka.actor.ActorSystem;
import akka.management.AkkaManagement$;
import akka.management.cluster.bootstrap.ClusterBootstrap$;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.knoldus.lagom.sample.restaurant.menu.api.MenuService;
import com.knoldus.lagom.sample.restaurant.order.api.OrderService;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.typesafe.config.Config;
import play.Application;
import play.Environment;

public final class OrderModule extends AbstractModule implements ServiceGuiceSupport {
  private final Environment environment;
  private final Config config;

  public OrderModule(final Environment environment, final Config config) {
    this.environment = environment;
    this.config = config;
  }

  @Override
  protected void configure() {
    if (environment.isProd()) {
      bind(AkkaManagerAndClusterStarter.class).asEagerSingleton();
    }

    bindService(OrderService.class, OrderServiceImpl.class);
  }

  static class AkkaManagerAndClusterStarter {
    @Inject
    AkkaManagerAndClusterStarter(final Application application, final ActorSystem actorSystem) {
      if (application.isProd()) {
        AkkaManagement$.MODULE$.get(actorSystem).start();
        ClusterBootstrap$.MODULE$.get(actorSystem).start();
      }
    }
  }
}
