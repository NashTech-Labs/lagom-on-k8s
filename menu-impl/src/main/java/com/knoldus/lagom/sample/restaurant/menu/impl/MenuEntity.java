package com.knoldus.lagom.sample.restaurant.menu.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

final class MenuEntity extends PersistentEntity<MenuCommand, MenuEvent, MenuState> {
  @Override
  public Behavior initialBehavior(Optional<MenuState> optionalMenuState) {

    BehaviorBuilder behaviorBuilder = newBehaviorBuilder(optionalMenuState.orElse(MenuState.builder().build()));

    behaviorBuilder.setCommandHandler(MenuCommand.AddItem.class, (cmd, ctx) ->
            ctx.thenPersist(MenuEvent.ItemAdded.builder().item(cmd.getItem()).build(), evt ->
                    ctx.reply(Done.getInstance())));

    return behaviorBuilder.build();
  }
}
