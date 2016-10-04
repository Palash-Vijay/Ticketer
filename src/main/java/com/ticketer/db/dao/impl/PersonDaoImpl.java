package com.ticketer.db.dao.impl;

import com.google.inject.Inject;
import com.ticketer.db.model.entities.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by akshay.kesarwan on 04/10/16.
 */
public class PersonDaoImpl extends AbstractDAO<Person> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public PersonDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Person findById(Long id) {
        return get(id);
    }

    public long create(Person person) {
        return persist(person).getId();
    }

    public List<Person> findAll() {
        return list(namedQuery("Person.findAll"));
    }
}
