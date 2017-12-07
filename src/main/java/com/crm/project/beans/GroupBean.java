package com.crm.project.beans;

import com.crm.project.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class GroupBean {

    @Autowired
    CompanyBean companyBean;
    @Autowired
    RoleBean roleBean;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String name, Long cid) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Company company = companyBean.getBy(cid);

            session.save(new Group(name, company));
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Group getBy(Long id) {
        Group group = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            group = session.find(Group.class, id);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return group;
    }

    public List<Group> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
            Root<Group> groupsTable = criteriaQuery.from(Group.class);
            criteriaQuery.select(groupsTable);
            criteriaQuery.where(builder.equal(groupsTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Group>();
    }

    public void update(Long id, String name) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Group group = session.find(Group.class, id);
            group.setName(name);
            session.update(group);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Group group) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.update(group);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Group group = session.find(Group.class, id);
            group.setActive(0);
            session.update(group);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<User> studentsOut(Company company) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            Join<User, GroupUser> groupUserJoin = usersTable.join("groupUsers", JoinType.LEFT);
            Predicate studentsPredicate = builder.equal(usersTable.get("role"), roleBean.getBy("student"));
            Predicate companyPredicate = builder.equal(usersTable.get("company"), company);
            Predicate outPredicate = groupUserJoin.isNull();
            Predicate activePredicate = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.select(usersTable);
            criteriaQuery.where(builder.and(studentsPredicate, companyPredicate, outPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            List<User> resultList = query.getResultList();
            session.close();

            return resultList;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<User> students(Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            Join<User, GroupUser> groupUserJoin = usersTable.join("groupUsers");
            Predicate studentsPredicate = builder.equal(usersTable.get("role"), roleBean.getBy("student"));
            Predicate groupPredicate = builder.equal(groupUserJoin.get("group"), group);
            Predicate activePredicate = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.select(usersTable);
            criteriaQuery.where(builder.and(studentsPredicate, groupPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            List<User> resultList = query.getResultList();
            session.close();

            return resultList;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<User> teachersOut(Company company, Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            Join<User, GroupUser> groupUserJoin = usersTable.join("groupUsers", JoinType.LEFT);
            Predicate teachersPredicate = builder.equal(usersTable.get("role"), roleBean.getBy("teacher"));
            Predicate companyPredicate = builder.equal(usersTable.get("company"), company);
            Predicate outPredicate = groupUserJoin.isNull();
            Predicate othersPredicate = builder.notEqual(groupUserJoin.get("group"), group);
            Predicate orPredicate = builder.or(outPredicate, othersPredicate);
            Predicate activePredicate = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.select(usersTable);
            criteriaQuery.where(builder.and(teachersPredicate, companyPredicate, orPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            List<User> resultList = query.getResultList();
            session.close();

            return resultList;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<User> teachers(Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            Join<User, GroupUser> groupUserJoin = usersTable.join("groupUsers");
            Predicate teachersPredicate = builder.equal(usersTable.get("role"), roleBean.getBy("teacher"));
            Predicate groupPredicate = builder.equal(groupUserJoin.get("group"), group);
            Predicate activePredicate = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.select(usersTable);
            criteriaQuery.where(builder.and(teachersPredicate, groupPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            List<User> resultList = query.getResultList();
            session.close();

            return resultList;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<Course> coursesOut(Company company, Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
            Root<Course> coursesTable = criteriaQuery.from(Course.class);
            Join<Course, GroupCourse> groupCourseJoin = coursesTable.join("groupCourses", JoinType.LEFT);
            Predicate companyPredicate = builder.equal(coursesTable.get("company"), company);
            Predicate outPredicate = groupCourseJoin.isNull();
            Predicate othersPredicate = builder.notEqual(groupCourseJoin.get("group"), group);
            Predicate orPredicate = builder.or(outPredicate, othersPredicate);
            Predicate activePredicate = builder.equal(coursesTable.get("active"), 1);
            criteriaQuery.select(coursesTable);
            criteriaQuery.where(builder.and(companyPredicate, orPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            List<Course> resultList = query.getResultList();
            session.close();

            return resultList;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Course>();
    }

    public List<Course> courses(Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
            Root<Course> coursesTable = criteriaQuery.from(Course.class);
            Join<Course, GroupCourse> groupCourseJoin = coursesTable.join("groupCourses");
            Predicate groupPredicate = builder.equal(groupCourseJoin.get("group"), group);
            Predicate activePredicate = builder.equal(coursesTable.get("active"), 1);
            criteriaQuery.select(coursesTable);
            criteriaQuery.where(builder.and(groupPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            List<Course> resultList = query.getResultList();
            session.close();

            return resultList;

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Course>();
    }
}
