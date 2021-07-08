/*
 * Copyright 2019 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.acme.reactive;

import java.net.URI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

@Path("coffee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeResource {

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @Inject
    PgPool client;

    @PostConstruct
    void config() {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {

                client.query("DROP TABLE IF EXISTS coffee").execute()
                .flatMap(r -> client.query("CREATE TABLE coffee (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
                .flatMap(r -> client.query("INSERT INTO coffee (name) VALUES ('Americano')").execute())
                .flatMap(r -> client.query("INSERT INTO coffee (name) VALUES ('Latte')").execute())
                .flatMap(r -> client.query("INSERT INTO coffee (name) VALUES ('Mocha')").execute()).await()
                .indefinitely();


    }

        @GET
    public Multi<Coffee> get() {
        return Coffee.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(@PathParam("id") Long id) {
        return Coffee.findById(client, id)
            .onItem().transform(fruit -> fruit != null ? Response.ok(fruit) : Response.status(Status.NOT_FOUND))
            .onItem().transform(ResponseBuilder::build);
    }



        @POST
    public Uni<Response> create(Coffee coffee) {
        return coffee.save(client)
                .onItem().transform(id -> URI.create("/coffee/" + id))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(@PathParam("id") Long id, Coffee coffee) {
        return coffee.update(client)
            .onItem().transform(updated -> updated ? Status.OK : Status.NOT_FOUND)
            .onItem().transform(status -> Response.status(status).build());
    }



        @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Coffee.delete(client, id)
                .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }


}
