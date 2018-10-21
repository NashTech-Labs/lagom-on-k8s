package com.knoldus.lagom.sample.restaurant.menu.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.List;

public interface MenuService extends Service {
  ServiceCall<NotUsed, List<String>> getMenu();

  ServiceCall<Item, Done> addItem();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return Service.named("menuService").withCalls(
            Service.pathCall("/menu",  this::getMenu),
            Service.pathCall("/menu",  this::addItem)
    ).withAutoAcl(true);
    // @formatter:on
  }
}
