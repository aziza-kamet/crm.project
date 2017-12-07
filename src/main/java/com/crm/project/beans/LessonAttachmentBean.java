package com.crm.project.beans;

import com.crm.project.dao.Lesson;
import com.crm.project.dao.LessonAttachment;
import com.crm.project.dao.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class LessonAttachmentBean {

    @Autowired
    LessonBean lessonBean;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String name, String mime, Integer size, Blob attachment, Date uploadDate, Long lid, User user) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Lesson lesson = lessonBean.getBy(lid);

            session.save(new LessonAttachment(name, mime, size, attachment, uploadDate, lesson, user));
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LessonAttachment getBy(Long id) {
        LessonAttachment lessonAttachment = null;

        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            lessonAttachment = session.find(LessonAttachment.class, id);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

        return lessonAttachment;
    }

    public List<LessonAttachment> getList() {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<LessonAttachment> criteriaQuery = builder.createQuery(LessonAttachment.class);
            Root<LessonAttachment> lessonAttachmentsTable = criteriaQuery.from(LessonAttachment.class);
            criteriaQuery.select(lessonAttachmentsTable);
            criteriaQuery.where(builder.equal(lessonAttachmentsTable.get("active"), 1));

            Query query = session.createQuery(criteriaQuery);

            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<LessonAttachment>();
    }

    public void update(Long id, String name, String mime, Integer size, Blob attachment, Date uploadDate, Long lid) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Lesson lesson = lessonBean.getBy(lid);
            LessonAttachment lessonAttachment = session.find(LessonAttachment.class, id);
            lessonAttachment.setName(name);
            lessonAttachment.setMime(mime);
            lessonAttachment.setSize(size);
            lessonAttachment.setAttachment(attachment);
            lessonAttachment.setUploadDate(uploadDate);
            lessonAttachment.setLesson(lesson);
            session.update(lessonAttachment);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            LessonAttachment lessonAttachment = session.find(LessonAttachment.class, id);
            session.delete(lessonAttachment);
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
