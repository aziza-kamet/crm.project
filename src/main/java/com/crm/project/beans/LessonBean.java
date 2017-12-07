package com.crm.project.beans;

import com.crm.project.dao.Course;
import com.crm.project.dao.Lesson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class LessonBean {

    @Autowired
    CourseBean courseBean;
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String title, String content, Long cid) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Course course = courseBean.getBy(cid);

            session.save(new Lesson(title, content, course));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Lesson getBy(Long id) {
        Lesson lesson = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            lesson = session.find(Lesson.class, id);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

        return lesson;
    }

    public List<Lesson> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Lesson> criteriaQuery = builder.createQuery(Lesson.class);
            Root<Lesson> lessonsTable = criteriaQuery.from(Lesson.class);
            criteriaQuery.select(lessonsTable);
            criteriaQuery.where(builder.equal(lessonsTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Lesson>();
    }

    public void update(Long id, String title, String content) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Lesson lesson = session.find(Lesson.class, id);
            lesson.setTitle(title);
            lesson.setContent(content);
            session.update(lesson);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Lesson lesson = session.find(Lesson.class, id);
            lesson.setActive(0);
            session.update(lesson);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
