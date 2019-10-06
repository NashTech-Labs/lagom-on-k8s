package com.knoldus.lagom.sample.restaurant.order.impl;

import com.google.inject.Inject;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.knoldus.lagom.sample.restaurant.menu.api.MenuService;
import com.knoldus.lagom.sample.restaurant.order.api.OrderService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

class OrderServiceImpl implements OrderService {
  private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

  private final PersistentEntityRegistry persistentEntityRegistry;
  private final MenuService menuService;

  @Inject
  OrderServiceImpl(final PersistentEntityRegistry persistentEntityRegistry,
                   final MenuService menuService,
                   final ReadSide readSide) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(OrderEntity.class);

    this.menuService = menuService;

    readSide.register(OrderEventProcessor.class);
  }

  @Override
  public ServiceCall<List<Item>, String> placeOrder() {
    return request -> {
      final String entityId = UUID.randomUUID().toString();
      menuService.getMenu().invoke().thenApply(menu -> {
        menu.forEach(item -> System.out.println("Item: " + item));
        return 1;
      })
      .exceptionally(throwable -> {
        LOGGER.error("Got exception >>>>>>>>>>> ", throwable);
        return 0;
      });

      return persistentEntityRegistry.refFor(OrderEntity.class, entityId)
              .ask(OrderCommand.PlaceOrder.builder().id(entityId).items(request).build());
    };
  }
}
