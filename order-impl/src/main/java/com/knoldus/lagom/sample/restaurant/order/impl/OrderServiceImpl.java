package com.knoldus.lagom.sample.restaurant.order.impl;

import com.google.inject.Inject;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.knoldus.lagom.sample.restaurant.order.api.OrderService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;

import java.util.List;
import java.util.UUID;

class OrderServiceImpl implements OrderService {

  private final PersistentEntityRegistry persistentEntityRegistry;

  @Inject
  OrderServiceImpl(final PersistentEntityRegistry persistentEntityRegistry,
                   final ReadSide readSide) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(OrderEntity.class);

    readSide.register(OrderEventProcessor.class);
  }

  @Override
  public ServiceCall<List<Item>, String> placeOrder() {
    return request -> {
      final String entityId = UUID.randomUUID().toString();
      return persistentEntityRegistry.refFor(OrderEntity.class, entityId)
              .ask(OrderCommand.PlaceOrder.builder().id(entityId).items(request).build());
    };
  }
}
