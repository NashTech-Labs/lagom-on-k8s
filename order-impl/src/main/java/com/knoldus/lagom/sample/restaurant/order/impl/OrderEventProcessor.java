package com.knoldus.lagom.sample.restaurant.order.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcollections.PSequence;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

final class OrderEventProcessor extends ReadSideProcessor<OrderEvent> {
    private static final Logger LOGGER = Logger.getLogger(OrderEventProcessor.class);

    private final CassandraSession cassandraSession;
    private final CassandraReadSide cassandraReadSide;
    private final ObjectMapper objectMapper;

    private PreparedStatement insertOrder;

    @Inject
    private OrderEventProcessor(final CassandraSession cassandraSession,
                                final CassandraReadSide cassandraReadSide,
                                final ObjectMapper objectMapper) {
        this.cassandraSession = cassandraSession;
        this.cassandraReadSide = cassandraReadSide;
        this.objectMapper = objectMapper;
    }

    @Override
    public ReadSideHandler<OrderEvent> buildHandler() {
        return cassandraReadSide.<OrderEvent>builder("order_offset")
                .setGlobalPrepare(this::createTable)
                .setPrepare(tag -> prepareInsertItem())
                .setEventHandler(OrderEvent.OrderPlaced.class, evt -> insertOrder(evt))
                .build();
    }

    @Override
    public PSequence<AggregateEventTag<OrderEvent>> aggregateTags() {
        return OrderEvent.TAG.allTags();
    }

    private CompletionStage<Done> createTable() {
        return cassandraSession.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS order ("
                        + "id text PRIMARY KEY,"
                        + "items set<text>"
                        + ")");
    }

    private CompletionStage<Done> prepareInsertItem() {
        return cassandraSession.prepare("INSERT INTO order (id, items) VALUES (?)")
                .thenApply(preparedStatement -> {
                    insertOrder = preparedStatement;
                    return Done.getInstance();
                });
    }

    private CompletionStage<List<BoundStatement>> insertOrder(OrderEvent.OrderPlaced orderPlaced) {
        return CassandraReadSide.completedStatement(insertOrder.bind(orderPlaced.getId())
                .bind(orderPlaced.getItems().stream().map(item -> {
                    try {
                        return objectMapper.writeValueAsString(item);
                    } catch (Exception ex) {
                        LOGGER.error("Caught exception while placing order: " + orderPlaced.getId(), ex);
                        return StringUtils.EMPTY;
                    }
                }).collect(Collectors.toList())));
    }
}
