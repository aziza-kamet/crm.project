package com.crm.project.beans;

import com.crm.project.dao.Course;
import com.crm.project.dao.Lesson;
import com.crm.project.dao.Mark;
import com.crm.project.dao.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;

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

    public Map<Long, Double> avgMarks(Lesson lesson) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root marksTable = criteriaQuery.from(Mark.class);
            Predicate lessonPredicate = builder.equal(marksTable.get("lesson"), lesson);
            Expression avgExpression = builder.avg(marksTable.<Integer> get("markValue"));
            criteriaQuery.multiselect(marksTable.get("user"), avgExpression).groupBy(marksTable.get("user"));
            criteriaQuery.where(builder.and(lessonPredicate));

            Query query = session.createQuery(criteriaQuery);
            ArrayList<Object[]> results = (ArrayList<Object[]>) query.getResultList();
            HashMap<Long, Double> marks = new HashMap<Long, Double>();

            System.out.println("size: " + results.size());

            for (Object[] obj:
                    results) {
                marks.put(((User)obj[0]).getId(), (Double) obj[1]);
            }

            return marks;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
