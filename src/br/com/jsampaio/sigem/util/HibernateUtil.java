/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.util;

import java.sql.Connection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Sophia
 */
public class HibernateUtil {
    
    private static final SessionFactory SESSION_FACTORY = cretateSessonFactory();

    private static SessionFactory cretateSessonFactory() {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            Configuration configuration = new Configuration().configure();

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
            return factory;
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Connection getConnection() {
        Session session = getSessionFactory().openSession();
        return session.doReturningWork((Connection con) -> con);
    }
    
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
