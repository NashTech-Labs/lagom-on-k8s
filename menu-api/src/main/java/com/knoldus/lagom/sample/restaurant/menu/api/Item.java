package com.knoldus.lagom.sample.restaurant.menu.api;

import lombok.Builder;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

@Value
@Builder
@JsonDeserialize
public final class Item {
  private final String name;
}
