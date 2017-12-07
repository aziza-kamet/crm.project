package com.crm.project.beans;

import com.crm.project.dao.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class CompanyBean {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String name, String description) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new Company(name, description));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Company getBy(Long id) {
        Company company = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            company = session.find(Company.class, id);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

        return company;
    }

    public List<Company> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Company> criteriaQuery = builder.createQuery(Company.class);
            Root<Company> companiesTable = criteriaQuery.from(Company.class);
            criteriaQuery.select(companiesTable);
            criteriaQuery.where(builder.equal(companiesTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Company>();
    }

    public void update(Long id, String name, String description) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Company company = session.find(Company.class, id);
            company.setName(name);
            company.setDescription(description);
            session.update(company);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Company company = session.find(Company.class, id);
            company.setActive(0);
            session.update(company);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
