package com.knoldus.lagom.sample.restaurant.order.api;

import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import lombok.Builder;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import java.util.List;

@Value
@Builder
@JsonDeserialize
public final class Order {
  private final List<Item> items;
}
