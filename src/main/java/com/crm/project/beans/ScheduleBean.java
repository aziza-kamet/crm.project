package com.crm.project.beans;

import com.crm.project.dao.Group;
import com.crm.project.dao.Schedule;
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

public class ScheduleBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    public void create(String name) {
//        try{
//
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//
//            session.save(new Schedule(name));
//            transaction.commit();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    public Schedule getBy(Long id) {
//        Schedule schedule = null;
//
//        try{
//
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//
//            schedule = session.find(Schedule.class, id);
//            transaction.commit();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return schedule;
//    }

//    public Schedule getBy(String name) {
//
//        try{
//
//            Session session = sessionFactory.openSession();
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//
//            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);
//            Root<Schedule> schedulesTable = criteriaQuery.from(Schedule.class);
//            criteriaQuery.select(schedulesTable);
//            criteriaQuery.where(builder.equal(schedulesTable.get("name"), name));
//
//            Query query = session.createQuery(criteriaQuery);
//
//            return (Schedule) query.getSingleResult();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public List<Schedule> getListByGroup(Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);
            Root<Schedule> schedulesTable = criteriaQuery.from(Schedule.class);
            Predicate groupPredicate = builder.equal(schedulesTable.get("group"), group);
            criteriaQuery.select(schedulesTable)
                    .where(groupPredicate);

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Schedule>();
    }

//    public void update(Long id, String name) {
//        try{
//
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//
//            Schedule schedule = session.find(Schedule.class, id);
//            schedule.setName(name);
//            session.update(schedule);
//            transaction.commit();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void delete(Long id) {
//        try{
//
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//
//            Schedule schedule = session.find(Schedule.class, id);
//            session.delete(schedule);
//            transaction.commit();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
