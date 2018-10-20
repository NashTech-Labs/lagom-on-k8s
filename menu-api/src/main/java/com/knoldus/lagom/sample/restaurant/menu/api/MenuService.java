package com.knoldus.lagom.sample.restaurant.menu.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.List;

public interface MenuService extends Service {
  ServiceCall<NotUsed, List<Item>> getMenu();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return Service.named("menuService").withCalls(
            Service.pathCall("/menu",  this::getMenu)
    ).withAutoAcl(true);
    // @formatter:on
  }
}
