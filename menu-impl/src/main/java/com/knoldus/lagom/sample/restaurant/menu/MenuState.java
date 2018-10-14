/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public final class MenuState implements CompressedJsonable {
    private final List<Item> menu;
}
