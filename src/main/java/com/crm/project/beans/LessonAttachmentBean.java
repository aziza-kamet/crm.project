package com.crm.project.beans;

import com.crm.project.dao.Lesson;
import com.crm.project.dao.LessonAttachment;
import com.crm.project.dao.User;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */
public class LessonAttachmentBean {

    @Autowired
    LessonBean lessonBean;

    private String[] extensions = new String[]{
            "jpg",
            "jpeg",
            "png",
            "doc",
            "docx",
            "xsl",
            "xslx",
            "ppt",
            "pptx",
            "pdf",
    };

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(String name, MultipartFile file, Long lid, User user) {
        try{

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Blob blob = Hibernate.getLobCreator(session).createBlob(file.getBytes());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if (!Arrays.asList(extensions).contains(extension)) {
                return;
            }

            if (file.getSize() > 5000000) {
                return;
            }

            Lesson lesson = lessonBean.getBy(lid);
            if (name == null || name.equals("")) {
                name = file.getOriginalFilename();
            } else {
                name = name + "." + extension;
            }

            session.save(new LessonAttachment(name, file.getContentType(), (int) file.getSize(), blob, lesson, user));
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

    public List<LessonAttachment> getList(Long id) {

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<LessonAttachment> criteriaQuery = builder.createQuery(LessonAttachment.class);
            Root<LessonAttachment> lessonAttachmentsTable = criteriaQuery.from(LessonAttachment.class);
            Predicate lessonPredicate = builder.equal(lessonAttachmentsTable.get("lesson"), lessonBean.getBy(id));
            criteriaQuery.select(lessonAttachmentsTable);
            criteriaQuery.where(builder.and(lessonPredicate));

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
