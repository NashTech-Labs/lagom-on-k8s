package com.knoldus.lagom.sample.restaurant.order.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Value;

import java.util.List;

interface OrderEvent extends Jsonable, AggregateEvent<OrderEvent> {
  AggregateEventShards<OrderEvent> TAG = AggregateEventTag.sharded(OrderEvent.class, 2);

  @Override
  default AggregateEventTagger<OrderEvent> aggregateTag() {
    return TAG;
  }

  @Value
  @Builder
  @JsonDeserialize
  final class OrderPlaced implements OrderEvent {
    private final String id;
    private final List<Item> items;
  }
}
