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

    public ArrayList<Attendance> getBy(Lesson lesson, User user) {

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

            return (ArrayList<Attendance>) query.getResultList();

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

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Attendance attendance = session.find(Attendance.class, id);

            session.delete(attendance);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<Long, ArrayList<Attendance>> getMap(Lesson lesson) {

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

    public HashMap<Long, HashMap> avgAndTotal(User user) {

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
            HashMap<Long, HashMap> attendances = new HashMap<Long, HashMap>();

            for (Object[] obj:
                    results) {
                Lesson lesson = (Lesson) obj[2];
                obj[1] = ((Double) obj[1]) * 100;



                HashMap contentMap = new HashMap();
                if (attendances.containsKey(lesson.getCourse().getId())) {
                    contentMap = attendances.get(lesson.getCourse().getId());
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

                attendances.put(lesson.getCourse().getId(), contentMap);
            }

            return attendances;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
