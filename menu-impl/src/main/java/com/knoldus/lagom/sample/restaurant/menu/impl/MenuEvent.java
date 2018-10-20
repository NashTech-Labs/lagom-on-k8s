package com.knoldus.lagom.sample.restaurant.menu.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;

interface MenuEvent extends Jsonable, AggregateEvent<MenuEvent> {

  AggregateEventShards<MenuEvent> TAG = AggregateEventTag.sharded(MenuEvent.class, 20);

  @Override
  default AggregateEventTagger<MenuEvent> aggregateTag() {
    return TAG;
  }

}
