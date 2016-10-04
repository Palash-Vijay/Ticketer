package com.ticketer;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
import javax.ws.rs.Path;
import java.util.Set;

/**
 * Created by akshay.kesarwan on 04/10/16.
 */

public class TicketerApplication extends Application<TicketerConfiguration> {

    public static void main(String args[]) throws Exception {
        new TicketerApplication().run(args);
    }

    private HibernateBundle<TicketerConfiguration> hibernateBundle = null;
    private final static String PACKAGE_URL = "com.ticketer";

    @Override
    public void initialize(Bootstrap<TicketerConfiguration> bootstrap) {
        Reflections reflections = new Reflections(PACKAGE_URL);
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
        Reflections reflections = new Reflections(PACKAGE_URL);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Path.class);
        System.out.println(controllers);
        for(Class<?> controller : controllers)
            environment.jersey().register(injector.getInstance(controller));
    }

}
