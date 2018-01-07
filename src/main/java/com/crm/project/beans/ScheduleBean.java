package com.crm.project.beans;

import com.crm.project.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
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

    public void create(Group group, Course course, Integer did, Integer hid, Date startDate, Date endDate, String notes) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new Schedule(did, hid, startDate, endDate, notes, group, course));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public List<Schedule> getListByUser(User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);
            Root<Schedule> schedulesTable = criteriaQuery.from(Schedule.class);
            Join<Schedule, Group> groupJoin = schedulesTable.join("group");
            Join<Group, GroupUser> groupUserJoin = groupJoin.join("groupUsers");
            Predicate userPredicate = builder.equal(groupUserJoin.get("user"), user);
            criteriaQuery.select(schedulesTable)
                    .where(userPredicate);

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Schedule>();
    }

    public void update(Long id, Group group, Course course, Integer did, Integer hid, Date startDate, Date endDate, String notes) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Schedule schedule = session.find(Schedule.class, id);
            schedule.setGroup(group);
            schedule.setCourse(course);
            schedule.setDayId(did);
            schedule.setHourId(hid);
            schedule.setStartDate(startDate);
            schedule.setEndDate(endDate);
            schedule.setNotes(notes);

            session.update(schedule);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Schedule schedule = session.find(Schedule.class, id);
            session.delete(schedule);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
