package com.redhat.example.weather;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RequestScoped
@Path("country")
public class CountryService {
    //TODO: Inject selected country

    //TODO: Expose REST service to set the selected country

}
