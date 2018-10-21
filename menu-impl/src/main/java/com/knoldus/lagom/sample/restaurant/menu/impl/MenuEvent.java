package com.knoldus.lagom.sample.restaurant.menu.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Value;

interface MenuEvent extends Jsonable, AggregateEvent<MenuEvent> {
  AggregateEventShards<MenuEvent> TAG = AggregateEventTag.sharded(MenuEvent.class, 20);

  @Override
  default AggregateEventTagger<MenuEvent> aggregateTag() {
    return TAG;
  }

  @Value
  @Builder
  @JsonDeserialize
  final class ItemAdded implements MenuEvent {
    private final Item item;
  }
}
