package com.knoldus.lagom.sample.restaurant.order.api;

import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;

public interface OrderService extends Service {
  ServiceCall<List<Item>, String> placeOrder();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return Service.named("orderService").withCalls(
            Service.restCall(Method.POST,"/order",  this::placeOrder)
    ).withAutoAcl(true);
    // @formatter:on
  }
}
