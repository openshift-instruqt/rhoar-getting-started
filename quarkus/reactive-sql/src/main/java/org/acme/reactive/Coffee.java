package org.acme.reactive;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

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

    public static Multi<Coffee> findAll(PgPool client) {
        return client.query("SELECT id, name FROM coffee ORDER BY name ASC").execute()
        .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
        .onItem().transform(Coffee::from);
    }

    // TODO FindById

    public Uni<Long> save(PgPool client) {
        return client.preparedQuery("INSERT INTO coffee (name) VALUES ($1) RETURNING (id)")
            .execute(Tuple.of(name))
            .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public Uni<Boolean> update(PgPool client) {
        return client.preparedQuery("UPDATE coffee SET name = $1 WHERE id = $2").execute(Tuple.of(name, id))
        .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(PgPool client, Long id) {
        return client.preparedQuery("DELETE FROM coffee WHERE id = $1").execute(Tuple.of(id))
        .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    private static Coffee from(Row row) {
        return new Coffee(row.getLong("id"), row.getString("name"));
    }
}