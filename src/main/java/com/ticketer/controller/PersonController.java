package com.ticketer.controller;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.ticketer.db.model.entities.Person;
import com.ticketer.service.api.PersonService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by akshay.kesarwan on 04/10/16.
 */
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {

    private PersonService personService;

    @Inject
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Person findPerson(@PathParam("id") LongParam id) {
        return personService.getPerson(id.get());
    }
}
