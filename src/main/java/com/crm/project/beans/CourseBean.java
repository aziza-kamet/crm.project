package com.crm.project.beans;

import com.crm.project.dao.*;
import jdk.nashorn.internal.scripts.JO;
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
public class CourseBean {

    @Autowired
    CompanyBean companyBean;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String name, String description, Long cid) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Company company = companyBean.getBy(cid);

            session.save(new Course(name, description, company));
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Course getBy(Long id) {
        Course course = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            course = session.find(Course.class, id);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return course;
    }

    public List<Course> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
            Root<Course> coursesTable = criteriaQuery.from(Course.class);
            criteriaQuery.select(coursesTable);
            criteriaQuery.where(builder.equal(coursesTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Course>();
    }

    public List<Lesson> getLessons(Course course) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Lesson> criteriaQuery = builder.createQuery(Lesson.class);
            Root<Lesson> lessonsTable = criteriaQuery.from(Lesson.class);
            Predicate predicates[] = {
                    builder.equal(lessonsTable.get("course"), course),
                    builder.equal(lessonsTable.get("active"), 1)
            };
            criteriaQuery.select(lessonsTable);
            criteriaQuery.where(builder.and(predicates));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Lesson>();
    }

    public void update(Long id, String name, String description) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Course course = session.find(Course.class, id);
            course.setName(name);
            course.setDescription(description);
            session.update(course);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Course course = session.find(Course.class, id);
            course.setActive(0);
            session.update(course);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Course> my(User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
            Root<User> userTable = criteriaQuery.from(User.class);
            Join<User, GroupUser> userGroupUserJoin = userTable.join("groupUsers");
            Join<GroupUser, Group> groupUserGroupJoin = userGroupUserJoin.join("group");
            Join<Group, GroupCourse> groupGroupCourseJoin = groupUserGroupJoin.join("groupCourses");
            Join<GroupCourse, Course> groupCourseCourseJoin = groupGroupCourseJoin.join("course");

            Predicate userPredicate = builder.equal(userTable.get("id"), user.getId());
            Predicate activePredicate = builder.equal(groupCourseCourseJoin.get("active"), 1);

            criteriaQuery.select(groupCourseCourseJoin).distinct(true);
            criteriaQuery.where(builder.and(userPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Course>();
    }

    public List<Group> groups(Course course, User user) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
            Root<User> userTable = criteriaQuery.from(User.class);
            Join<User, GroupUser> userGroupUserJoin = userTable.join("groupUsers");
            Join<GroupUser, Group> groupUserGroupJoin = userGroupUserJoin.join("group");
            Join<Group, GroupCourse> groupGroupCourseJoin = groupUserGroupJoin.join("groupCourses");

            Predicate userPredicate = builder.equal(userTable.get("id"), user.getId());
            Predicate coursePredicate = builder.equal(groupGroupCourseJoin.get("course"), course);
            Predicate activePredicate = builder.equal(groupUserGroupJoin.get("active"), 1);

            criteriaQuery.select(groupUserGroupJoin).distinct(true);
            criteriaQuery.where(builder.and(userPredicate, coursePredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Group>();
    }
}
