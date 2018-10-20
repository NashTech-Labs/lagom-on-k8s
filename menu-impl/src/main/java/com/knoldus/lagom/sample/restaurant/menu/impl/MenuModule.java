package com.knoldus.lagom.sample.restaurant.menu.impl;

import com.google.inject.AbstractModule;
import com.knoldus.lagom.sample.restaurant.menu.api.MenuService;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public final class MenuModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindService(MenuService.class, MenuServiceImpl.class);
  }
}
