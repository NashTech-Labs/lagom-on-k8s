package com.knoldus.lagom.sample.restaurant.order.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

final class OrderEntity extends PersistentEntity<OrderCommand, OrderEvent, OrderState> {
  @Override
  public Behavior initialBehavior(Optional<OrderState> optionalOrderState) {

    BehaviorBuilder behaviorBuilder = newBehaviorBuilder(optionalOrderState.orElse(OrderState.builder().build()));

    behaviorBuilder.setCommandHandler(OrderCommand.PlaceOrder.class, (cmd, ctx) ->
      ctx.thenPersist(OrderEvent.OrderPlaced.builder().items(cmd.getItems()).id(cmd.getId()).build(), evt ->
              ctx.reply(cmd.getId())));

    behaviorBuilder.setCommandHandler(OrderCommand.PlaceOrder.class, (cmd, ctx) ->
            ctx.thenPersist(OrderEvent.OrderPlaced.builder().items(cmd.getItems()).id(cmd.getId()).build(), evt ->
                    ctx.reply(cmd.getId())));

    return behaviorBuilder.build();
  }
}
