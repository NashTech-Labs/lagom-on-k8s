/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface MenuEvent extends Jsonable, AggregateEvent<MenuEvent> {

    int NUM_SHARDS = 20; //TODO: Should be static and decided before deploying application on production

    AggregateEventShards<MenuEvent> TAG = AggregateEventTag.sharded(MenuEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<MenuEvent> aggregateTag() {
        return TAG;
    }

    /**
     * Event generated when an Item gets added to Menu.
     */
    @Getter
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    final class ItemAdded implements MenuEvent, CompressedJsonable {
        private final Item item;
    }

}
