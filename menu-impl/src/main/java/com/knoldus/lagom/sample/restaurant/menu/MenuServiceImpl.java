/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import akka.Done;
import akka.NotUsed;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;

import java.util.List;
import java.util.function.Function;

public final class MenuServiceImpl implements MenuService {
    private final PersistentEntityRegistry registry;

    @Inject
    public MenuServiceImpl(final PersistentEntityRegistry registry, final ReadSide readSide) {
        this.registry = registry;
        this.registry.register(MenuEntity.class);

        readSide.register(MenuEventProcessor.class);
    }

    @Override
    public ServiceCall<Item, Done> addItem() {
        return item ->
                registry.refFor(MenuEntity.class, item.getName())
                        .ask(MenuCommand.AddItem.builder().item(item).build())
                        .thenApply(Function.identity());
    }

    @Override
    public ServiceCall<NotUsed, List<Item>> getMenu() {
        return null;
    }

    @Override
    public ServiceCall<Item, Done> deleteItem() {
        return null;
    }
}
