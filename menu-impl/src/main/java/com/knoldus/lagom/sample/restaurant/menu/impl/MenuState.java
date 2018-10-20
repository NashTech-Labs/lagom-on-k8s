package com.knoldus.lagom.sample.restaurant.menu.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize
final class MenuState implements CompressedJsonable {
}
