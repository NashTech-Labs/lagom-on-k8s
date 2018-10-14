/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import akka.Done;
import akka.stream.javadsl.Source;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface MenuService extends Service {

    ServiceCall<Item, Done> addItem();

    ServiceCall<NotUsed, List<Item>> getMenu();

    ServiceCall<Item, Done> deleteItem();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("menuservice").withCalls(
                Service.restCall(Method.POST, "/api/menu/item", this::addItem),
                Service.restCall(Method.GET, "/api/menu", this::getMenu),
                Service.restCall(Method.DELETE, "/api/menu/item", this::deleteItem)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
