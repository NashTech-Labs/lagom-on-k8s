package com.knoldus.lagom.sample.restaurant.order.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Value;

import java.util.List;

interface OrderCommand extends Jsonable {
    @Value
    @Builder
    @JsonDeserialize
    final class PlaceOrder implements OrderCommand, CompressedJsonable, PersistentEntity.ReplyType<String> {
        private final String id;
        private final List<Item> items;
    }
}
