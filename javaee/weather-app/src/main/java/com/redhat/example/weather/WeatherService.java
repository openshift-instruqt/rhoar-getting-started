package com.redhat.example.weather;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

@RequestScoped
@Path("weather")
public class WeatherService {

    static final City[] cities= new City[] {
            new City("lon","London","rainy-3",8,8,10,6,30,2),
            new City("man","Manchester","sunny",12,10,14,10,0,7)
    };

    static final Country england = new Country("en","England");

    static {
        england.setCities(Arrays.<City>asList(cities));
    }

    //TODO: Inject selected country

    //TODO: Inject EntityManager

    //TODO: Expose REST GET service get country as JSON
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Country getList() {
        return england; //TODO: Replace with call to database
    }


}
