package com.crm.project.beans;

import org.hibernate.SessionFactory;

/**
 * Created by aziza on 03.11.17.
 */
public class NewsBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
