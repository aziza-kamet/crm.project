package com.crm.project.beans;

import com.crm.project.dao.Lesson;
import com.crm.project.dao.Mark;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.crm.project.dao.User;
import org.hibernate.Transaction;
import sun.net.www.content.text.Generic;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class MarkBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Lesson lesson, User user, Integer grade, String note) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new Mark(grade, note, lesson, user));
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Mark> getBy(Lesson lesson, User user) {

        try{

            System.out.println("uid = " + user.getId());
            System.out.println("lid = " + lesson.getId());
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Mark> criteriaQuery = builder.createQuery(Mark.class);
            Root<Mark> marksTable = criteriaQuery.from(Mark.class);
            Predicate lessonPredicate = builder.equal(marksTable.get("lesson"), lesson);
            Predicate userPredicate = builder.equal(marksTable.get("user"), user);
            criteriaQuery.select(marksTable);
            criteriaQuery.where(builder.and(lessonPredicate, userPredicate));

            Query query = session.createQuery(criteriaQuery);

            return (ArrayList<Mark>) query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void update(Mark mark) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.update(mark);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Mark mark = session.find(Mark.class, id);

            session.delete(mark);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<Long, ArrayList<Mark>> getMap(Lesson lesson) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Mark> criteriaQuery = builder.createQuery(Mark.class);
            Root<Mark> marksTable = criteriaQuery.from(Mark.class);
            Predicate lessonPredicate = builder.equal(marksTable.get("lesson"), lesson);
            criteriaQuery.select(marksTable);
            criteriaQuery.where(builder.and(lessonPredicate));

            Query query = session.createQuery(criteriaQuery);
            ArrayList<Mark> results = (ArrayList<Mark>) query.getResultList();
            HashMap<Long, ArrayList<Mark>> marks = new HashMap<Long, ArrayList<Mark>>();

            for (Mark mark:
                 results) {
                ArrayList<Mark> mrks = new ArrayList<Mark>();
                if (marks.containsKey(mark.getUser().getId())) {
                    mrks = marks.get(mark.getUser().getId());
                }
                mrks.add(mark);
                marks.put(mark.getUser().getId(), mrks);
            }

            return marks;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new HashMap<Long, ArrayList<Mark>>();
    }

    public HashMap<Long, HashMap> avgAndTotal(User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root marksTable = criteriaQuery.from(Mark.class);
            Predicate userPredicate = builder.equal(marksTable.get("user"), user);
            Expression totalExpression = builder.sum(marksTable.<Integer> get("markValue"));
            Expression avgExpression = builder.avg(marksTable.<Integer> get("markValue"));
            criteriaQuery.multiselect(totalExpression, avgExpression, marksTable.get("lesson")).groupBy(marksTable.get("lesson"));
            criteriaQuery.where(builder.and(userPredicate));

            Query query = session.createQuery(criteriaQuery);
            ArrayList<Object[]> results = (ArrayList<Object[]>) query.getResultList();
            HashMap<Long, HashMap> marks = new HashMap<Long, HashMap>();

            for (Object[] obj:
                 results) {
                Lesson lesson = (Lesson) obj[2];
                HashMap contentMap = new HashMap();
                if (marks.containsKey(lesson.getCourse().getId())) {
                    contentMap = marks.get(lesson.getCourse().getId());
                }

                HashMap<Long, Object[]> lessons = new HashMap<Long, Object[]>();
                if (contentMap.containsKey("lessons")) {
                    lessons = (HashMap<Long, Object[]>) contentMap.get("lessons");
                }

                Double avg = 0.0;
                if (contentMap.containsKey("avg")) {
                    avg = (Double) contentMap.get("avg");
                }
                Double sum = ((Double)obj[1]);
                if (contentMap.containsKey("sum")) {
                    sum  = (Double) contentMap.get("sum") + ((Double)obj[1]);
                }
                lessons.put(lesson.getId(), obj);
                contentMap.put("lessons", lessons);
                contentMap.put("sum", sum);

                if (sum != 0) {
                    avg = Double.valueOf(sum / lessons.size());
                }
                contentMap.put("avg", avg);

                marks.put(lesson.getCourse().getId(), contentMap);
            }

            return marks;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
