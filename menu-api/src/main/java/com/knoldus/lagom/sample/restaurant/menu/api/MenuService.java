package com.knoldus.lagom.sample.restaurant.menu.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;

public interface MenuService extends Service {
  ServiceCall<NotUsed, List<String>> getMenu();

  ServiceCall<Item, Done> addItem();

  ServiceCall<Item, Done> deleteItem();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return Service.named("menuService").withCalls(
            Service.restCall(Method.GET, "/menu",  this::getMenu),
            Service.restCall(Method.POST,"/menu/item",  this::addItem),
            Service.restCall(Method.DELETE,"/menu/item",  this::deleteItem)
    ).withAutoAcl(true);
    // @formatter:on
  }
}
