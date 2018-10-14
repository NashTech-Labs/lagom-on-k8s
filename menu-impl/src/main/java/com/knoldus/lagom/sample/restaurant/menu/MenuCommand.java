/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Getter;

public interface MenuCommand extends Jsonable {

    @Getter
    @Builder
    @JsonDeserialize
    final class AddItem implements MenuCommand, PersistentEntity.ReplyType<Done> {
        private final Item item;
    }

}
