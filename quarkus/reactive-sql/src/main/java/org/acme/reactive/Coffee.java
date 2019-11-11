package org.acme.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import io.vertx.axle.pgclient.PgPool;
import io.vertx.axle.sqlclient.Row;
import io.vertx.axle.sqlclient.RowSet;
import io.vertx.axle.sqlclient.Tuple;

public class Coffee {

    public Long id;

    public String name;

    public Coffee() {
    }

    public Coffee(String name) {
        this.name = name;
    }

    public Coffee(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CompletionStage<List<Coffee>> findAll(PgPool client) {
        return client.query("SELECT id, name FROM coffee ORDER BY name ASC").thenApply(pgRowSet -> {
            List<Coffee> list = new ArrayList<>(pgRowSet.size());
            for (Row row : pgRowSet) {
                list.add(from(row));
            }
            return list;
        });
    }

    // TODO FindById

    public CompletionStage<Long> save(PgPool client) {
        return client.preparedQuery("INSERT INTO coffee (name) VALUES ($1) RETURNING (id)", Tuple.of(name))
                .thenApply(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public CompletionStage<Boolean> update(PgPool client) {
        return client.preparedQuery("UPDATE coffee SET name = $1 WHERE id = $2", Tuple.of(name, id))
                .thenApply(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static CompletionStage<Boolean> delete(PgPool client, Long id) {
        return client.preparedQuery("DELETE FROM coffee WHERE id = $1", Tuple.of(id))
                .thenApply(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    private static Coffee from(Row row) {
        return new Coffee(row.getLong("id"), row.getString("name"));
    }
}