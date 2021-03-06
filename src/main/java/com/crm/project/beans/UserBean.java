package com.crm.project.beans;

import com.crm.project.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class UserBean {
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

    public boolean create(String login, String password, String name,
                       String surname, Long roleId, Long companyId) {
        try{

            Company company = companyBean.getBy(companyId);
            if (getByCompanyLogin(company.getAppendedLogin(login)) == null) {

                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                Role role = roleBean.getBy(roleId);

                session.save(new User(login, company.getAppendedLogin(login), getEncrypted(password), name, surname, company, role));
                transaction.commit();

                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public User getBy(Long id) {
        User user = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            user = session.find(User.class, id);
            transaction.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public User getBy(String login, String password) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            criteriaQuery.select(usersTable);
            Predicate loginPredicate = builder.equal(usersTable.get("companyLogin"), login);
            Predicate passwordPredicate = builder.equal(usersTable.get("password"), getEncrypted(password));
            Predicate activePredicate = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.where(builder.and(loginPredicate, passwordPredicate, activePredicate));

            Query query = session.createQuery(criteriaQuery);

            return (User) query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public User getByCompanyLogin(String login) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            Predicate userPredicates[] = {
                    builder.equal(usersTable.get("companyLogin"), login),
                    builder.equal(usersTable.get("active"), 1)
            };
            criteriaQuery.select(usersTable);
            criteriaQuery.where(builder.and(userPredicates));

            Query query = session.createQuery(criteriaQuery);

            return (User) query.getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            criteriaQuery.select(usersTable);
            criteriaQuery.where(builder.equal(usersTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<User> getListOfAdmins() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            criteriaQuery.select(usersTable);
            Predicate role = builder.equal(usersTable.get("role"), roleBean.getBy("admin"));
            Predicate active = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.where(builder.and(role, active));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<User> getListOfTeachers(Long cid) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            criteriaQuery.select(usersTable);
            Predicate role = builder.equal(usersTable.get("role"), roleBean.getBy("teacher"));
            Predicate company = builder.equal(usersTable.get("company"), companyBean.getBy(cid));
            Predicate active = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.where(builder.and(role, company, active));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public List<User> getListOfStudents(Long cid) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> usersTable = criteriaQuery.from(User.class);
            criteriaQuery.select(usersTable);
            Predicate role = builder.equal(usersTable.get("role"), roleBean.getBy("student"));
            Predicate company = builder.equal(usersTable.get("company"), companyBean.getBy(cid));
            Predicate active = builder.equal(usersTable.get("active"), 1);
            criteriaQuery.where(builder.and(role, company, active));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    public void update(Long id, String login, String name, String surname, Long roleId, Long companyId) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Company company = companyBean.getBy(companyId);

            User user = session.find(User.class, id);
            user.setLogin(login);
            user.setCompanyLogin(company.getAppendedLogin(login));
            user.setName(name);
            user.setSurname(surname);
            user.setCompany(company);
            user.setRole(session.find(Role.class, roleId));
            session.update(user);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updatePassword(Long id, String password) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            User user = session.find(User.class, id);
            user.setPassword(getEncrypted(password));
            session.update(user);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasCourse(User user, Long cid) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
            Root<Course> coursesTable = criteriaQuery.from(Course.class);

            if (user.getRole().getName().equals("admin")) {
                Predicate predicates[] = {
                        builder.equal(coursesTable.get("id"), cid),
                        builder.equal(coursesTable.get("company"), user.getCompany())
                };
                criteriaQuery.select(coursesTable);
                criteriaQuery.where(builder.and(predicates));
            } else {

                Join<Course, GroupCourse> groupCoursesTable = coursesTable.join("groupCourses");
                Join<GroupCourse, Group> groupsTable = groupCoursesTable.join("group");
                Join<Group, GroupUser> groupUsersTable = groupsTable.join("groupUsers");
                Predicate coursePredicate = builder.equal(coursesTable.get("id"), cid);
                Predicate userPredicate = builder.equal(groupUsersTable.get("user"), user);
                Predicate activePredicate = builder.equal(coursesTable.get("active"), 1);
                criteriaQuery.select(coursesTable);
                criteriaQuery.where(builder.and(coursePredicate, userPredicate, activePredicate));
            }
            Query query = session.createQuery(criteriaQuery);

            List list = query.getResultList();
            return list.size() != 0;

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean hasGroup(User user, Group group) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
            if (user.getRole().getName().equals("admin")) {
                Root<Group> groupsTable = criteriaQuery.from(Group.class);
                Predicate predicates[] = {
                        builder.equal(groupsTable.get("id"), group.getId()),
                        builder.equal(groupsTable.get("company"), user.getCompany())
                };
                criteriaQuery.select(groupsTable);
                criteriaQuery.where(builder.and(predicates));
            } else {
                Root<Group> groupsTable = criteriaQuery.from(Group.class);
                Join<Group, GroupUser> groupUsersTable = groupsTable.join("groupUsers");
                Predicate predicates[] = {
                        builder.equal(groupUsersTable.get("group"), group),
                        builder.equal(groupUsersTable.get("user"), user),
                        builder.equal(groupsTable.get("active"), 1)
                };
                criteriaQuery.select(groupsTable);
                criteriaQuery.where(builder.and(predicates));
            }
            Query query = session.createQuery(criteriaQuery);

            List list = query.getResultList();
            return list.size() != 0;

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            User user = session.find(User.class, id);
            user.setActive(0);
            session.update(user);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getEncrypted(String originalString) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    org.apache.commons.io.IOUtils.toByteArray(
                            new StringReader(originalString), "UTF-8"
                    ));

            StringBuffer hexString = new StringBuffer();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
