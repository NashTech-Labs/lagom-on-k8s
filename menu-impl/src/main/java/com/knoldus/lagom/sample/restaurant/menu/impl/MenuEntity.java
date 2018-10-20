package com.knoldus.lagom.sample.restaurant.menu.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

final class MenuEntity extends PersistentEntity<MenuCommand, MenuEvent, MenuState> {
  @Override
  public Behavior initialBehavior(Optional<MenuState> optionalMenuState) {

    BehaviorBuilder behaviorBuilder = newBehaviorBuilder(optionalMenuState.orElse(MenuState.builder().build()));

    return behaviorBuilder.build();
  }
}
