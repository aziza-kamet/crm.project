package com.crm.project.beans;

import com.crm.project.dao.SuperAdmin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziza on 10.11.17.
 */
public class SuperAdminBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void create(String login, String password) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new SuperAdmin(login, getEncrypted(password)));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SuperAdmin getBy(Long id) {
        SuperAdmin superAdmin = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            superAdmin = session.find(SuperAdmin.class, id);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

        return superAdmin;
    }

    public SuperAdmin getBy(String login, String password) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<SuperAdmin> criteriaQuery = builder.createQuery(SuperAdmin.class);
            Root<SuperAdmin> superAdminsTable = criteriaQuery.from(SuperAdmin.class);
            criteriaQuery.select(superAdminsTable);
            Predicate loginPredicate = builder.equal(superAdminsTable.get("login"), login);
            Predicate passwordPredicate = builder.equal(superAdminsTable.get("password"), getEncrypted(password));
            Predicate activePredicate = builder.equal(superAdminsTable.get("active"), 1);
            criteriaQuery.where(builder.and(loginPredicate, passwordPredicate, activePredicate));


            Query query = session.createQuery(criteriaQuery);

            return (SuperAdmin) query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<SuperAdmin> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<SuperAdmin> criteriaQuery = builder.createQuery(SuperAdmin.class);
            Root<SuperAdmin> superAdminsTable = criteriaQuery.from(SuperAdmin.class);
            criteriaQuery.select(superAdminsTable);
            criteriaQuery.where(builder.equal(superAdminsTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<SuperAdmin>();
    }

    public void update(Long id, String login) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            SuperAdmin superAdmin = session.find(SuperAdmin.class, id);
            superAdmin.setLogin(login);
            session.update(superAdmin);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updatePassword(Long id, String password) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            SuperAdmin superAdmin = session.find(SuperAdmin.class, id);
            superAdmin.setPassword(getEncrypted(password));
            session.update(superAdmin);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            SuperAdmin superAdmin = session.find(SuperAdmin.class, id);
            superAdmin.setActive(0);
            session.update(superAdmin);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getEncrypted(String originalString) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    org.apache.commons.io.IOUtils.toByteArray(
                            new StringReader(originalString), "UTF-8"
                    ));

            StringBuffer hexString = new StringBuffer();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
