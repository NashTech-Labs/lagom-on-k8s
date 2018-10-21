package com.knoldus.lagom.sample.restaurant.menu.impl;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Value;

interface MenuCommand extends Jsonable {
    @Value
    @Builder
    @JsonDeserialize
    final class AddItem implements MenuCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        private final Item item;
    }
}
