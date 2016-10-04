package com.ticketer;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ticketer.controller.PersonController;
import com.ticketer.service.api.PersonService;
import com.ticketer.service.impl.PersonServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;

import javax.persistence.Entity;

/**
 * Created by akshay.kesarwan on 04/10/16.
 */

public class TicketerApplication extends Application<TicketerConfiguration> {

    public static void main(String args[]) throws Exception {
        new TicketerApplication().run(args);
    }

    HibernateBundle<TicketerConfiguration> hibernateBundle = null;

    @Override
    public void initialize(Bootstrap<TicketerConfiguration> bootstrap) {
        Reflections reflections = new Reflections("com.ticketer");
        ImmutableList<Class<?>> entities = ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
        hibernateBundle = new HibernateBundle<TicketerConfiguration>(entities, new SessionFactoryFactory()) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(TicketerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(TicketerConfiguration configuration, Environment environment) throws Exception {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());
                bind(PersonService.class).to(PersonServiceImpl.class).asEagerSingleton();
            }
        });
        environment.jersey().register(injector.getInstance(PersonController.class));
    }

}
