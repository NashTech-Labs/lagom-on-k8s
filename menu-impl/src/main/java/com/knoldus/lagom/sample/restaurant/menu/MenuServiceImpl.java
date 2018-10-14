/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import akka.Done;
import akka.NotUsed;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import java.util.List;

public final class MenuServiceImpl implements MenuService {
    private final PersistentEntityRegistry registry;

    @Inject
    public MenuServiceImpl(final PersistentEntityRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ServiceCall<Item, Done> addItem() {
        return null;
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
