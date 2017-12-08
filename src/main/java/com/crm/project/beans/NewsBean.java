package com.crm.project.beans;

import com.crm.project.dao.Company;
import com.crm.project.dao.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.crm.project.dao.User;
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
public class NewsBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(User user, String title, String content) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Company company = user.getCompany();

            session.save(new News(title, content, company, user));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public List<News> getListOf(User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<News> criteriaQuery = builder.createQuery(News.class);
            Root<News> newsTable = criteriaQuery.from(News.class);
            Predicate companyPredicate = builder.equal(newsTable.get("company"), user.getCompany());
            Predicate activePredicate = builder.equal(newsTable.get("active"), 1);
            criteriaQuery.select(newsTable);
            criteriaQuery.where(builder.and(companyPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<News>();
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            News news = session.find(News.class, id);
            news.setActive(0);
            session.update(news);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Long id, String title, String content) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            News news = session.find(News.class, id);
            news.setTitle(title);
            news.setContent(content);
            session.update(news);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
