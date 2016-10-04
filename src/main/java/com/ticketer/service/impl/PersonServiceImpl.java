package com.ticketer.service.impl;

import com.google.inject.Inject;
import com.ticketer.db.dao.PersonDao;
import com.ticketer.db.model.entities.Person;
import com.ticketer.service.api.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by akshay.kesarwan on 04/10/16.
 */
public class PersonServiceImpl implements PersonService {

    Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    PersonDao personDao;

    @Inject
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person getPerson(Long id) {
        logger.debug("Get Person Service called for " + id);
        return personDao.findById(id);
    }
}
