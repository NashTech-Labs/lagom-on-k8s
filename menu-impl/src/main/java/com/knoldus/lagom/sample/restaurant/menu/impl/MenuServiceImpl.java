package com.knoldus.lagom.sample.restaurant.menu.impl;

import akka.Done;
import akka.NotUsed;
import com.google.inject.Inject;
import com.knoldus.lagom.sample.restaurant.menu.api.Item;
import com.knoldus.lagom.sample.restaurant.menu.api.MenuService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import java.util.List;
import java.util.stream.Collectors;

class MenuServiceImpl implements MenuService {

  private final PersistentEntityRegistry persistentEntityRegistry;
  private final CassandraSession cassandraSession;

  @Inject
  MenuServiceImpl(final PersistentEntityRegistry persistentEntityRegistry,
                  final CassandraSession cassandraSession,
                  final ReadSide readSide) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(MenuEntity.class);

    this.cassandraSession = cassandraSession;

    readSide.register(MenuEventProcessor.class);
  }

  @Override
  public ServiceCall<NotUsed, List<String>> getMenu() {
    return request ->
            cassandraSession.selectAll("select * from menu")
                    .thenApply(rows ->
                            rows.stream().map(row -> row.getString("name")).collect(Collectors.toList()));
  }

  @Override
  public ServiceCall<Item, Done> addItem() {
    return request ->
            persistentEntityRegistry.refFor(MenuEntity.class, request.getName())
                    .ask(MenuCommand.AddItem.builder().item(request).build());
  }

  @Override
  public ServiceCall<Item, Done> deleteItem() {
    return request ->
            persistentEntityRegistry.refFor(MenuEntity.class, request.getName())
                    .ask(MenuCommand.DeleteItem.builder().item(request).build());
  }
}
