/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public final class MenuEntity extends PersistentEntity<MenuCommand, MenuEvent, MenuState> {

    @SuppressWarnings("unchecked")
    @Override
    public Behavior initialBehavior(final Optional<MenuState> circleStateOptional) {

        BehaviorBuilder behaviorBuilder = newBehaviorBuilder(
                circleStateOptional.orElse(MenuState.builder().build()));

        /**
         * Persists event into cassandra.
         */
        behaviorBuilder.setCommandHandler(MenuCommand.AddItem.class, (cmd, ctx) ->
                ctx.thenPersist(MenuEvent.ItemAdded.builder()
                                .item(cmd.getItem()).build(),
                        evt -> ctx.reply(Done.getInstance())));

        behaviorBuilder.setEventHandler(MenuEvent.ItemAdded.class, evt -> {
            state().getMenu().add(evt.getItem());
            return state();
        });

        return behaviorBuilder.build();
    }

}
