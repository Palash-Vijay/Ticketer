package com.ticketer.service.api;

import com.ticketer.db.model.entities.Person;

/**
 * Created by akshay.kesarwan on 04/10/16.
 */
public interface PersonService {
    Person getPerson(Long id);
}
