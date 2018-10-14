package com.knoldus.lagom.sample.restaurant.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
final class Item {
    private final String name;
}
