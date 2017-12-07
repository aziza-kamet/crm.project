package com.crm.project.beans;

import com.crm.project.dao.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */

public class RoleBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String name) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new Role(name));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Role getBy(Long id) {
        Role role = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            role = session.find(Role.class, id);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

        return role;
    }

    public Role getBy(String name) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
            Root<Role> rolesTable = criteriaQuery.from(Role.class);
            criteriaQuery.select(rolesTable);
            criteriaQuery.where(builder.equal(rolesTable.get("name"), name));

            Query query = session.createQuery(criteriaQuery);

            return (Role) query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<Role> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
            Root<Role> rolesTable = criteriaQuery.from(Role.class);
            criteriaQuery.select(rolesTable);

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Role>();
    }

    public void update(Long id, String name) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Role role = session.find(Role.class, id);
            role.setName(name);
            session.update(role);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Role role = session.find(Role.class, id);
            session.delete(role);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
