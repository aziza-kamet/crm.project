package com.crm.project.beans;

import com.crm.project.dao.Attendance;
import com.crm.project.dao.Lesson;
import com.crm.project.dao.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aziza on 03.11.17.
 */
public class AttendanceBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Lesson lesson, User user, Integer attendanceValue, String note) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new Attendance(attendanceValue, note, lesson, user));
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Attendance getBy(Lesson lesson, User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Attendance> criteriaQuery = builder.createQuery(Attendance.class);
            Root<Attendance> attendancesTable = criteriaQuery.from(Attendance.class);
            Predicate lessonPredicate = builder.equal(attendancesTable.get("lesson"), lesson);
            Predicate userPredicate = builder.equal(attendancesTable.get("user"), user);
            criteriaQuery.select(attendancesTable);
            criteriaQuery.where(builder.and(lessonPredicate, userPredicate));

            Query query = session.createQuery(criteriaQuery);

            return (Attendance) query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void update(Attendance attendance) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.update(attendance);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<Long, ArrayList<Attendance>> getList(Lesson lesson) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Attendance> criteriaQuery = builder.createQuery(Attendance.class);
            Root<Attendance> attendancesTable = criteriaQuery.from(Attendance.class);
            Predicate lessonPredicate = builder.equal(attendancesTable.get("lesson"), lesson);
            criteriaQuery.select(attendancesTable);
            criteriaQuery.where(builder.and(lessonPredicate));

            Query query = session.createQuery(criteriaQuery);
            ArrayList<Attendance> results = (ArrayList<Attendance>) query.getResultList();
            HashMap<Long, ArrayList<Attendance>> attendances = new HashMap<Long, ArrayList<Attendance>>();

            for (Attendance attendance:
                    results) {
                ArrayList<Attendance> atts = new ArrayList<Attendance>();
                if (attendances.containsKey(attendance.getUser().getId())) {
                    atts = attendances.get(attendance.getUser().getId());
                }
                atts.add(attendance);
                attendances.put(attendance.getUser().getId(), atts);
            }

            return attendances;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new HashMap<Long, ArrayList<Attendance>>();
    }

    public ArrayList<Object[]> avgAndTotal(User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root attendancesTable = criteriaQuery.from(Attendance.class);
            Predicate userPredicate = builder.equal(attendancesTable.get("user"), user);
            Expression totalExpression = builder.sum(attendancesTable.<Integer> get("attendanceValue"));
            Expression avgExpression = builder.avg(attendancesTable.<Integer> get("attendanceValue"));
            criteriaQuery.multiselect(totalExpression, avgExpression, attendancesTable.get("lesson")).groupBy(attendancesTable.get("lesson"));
            criteriaQuery.where(builder.and(userPredicate));

            Query query = session.createQuery(criteriaQuery);
            ArrayList<Object[]> results = (ArrayList<Object[]>) query.getResultList();
            HashMap<Long, Object[]> attendances = new HashMap<Long, Object[]>();

            for (Object[] obj:
                    results) {
                Lesson lesson = (Lesson) obj[2];
                attendances.put(lesson.getId(), obj);
            }

            return results;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Object[]>();
    }
}
