package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;

@Path("item")
public class ItemResource {

    @Inject
    ItemService service;

    @Inject
    @Location("ItemResource/item")
    Template item;

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("id") Integer id) {
        return item.data("item", service.findItem(id));
    }
}
