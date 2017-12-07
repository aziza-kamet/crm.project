package com.crm.project.dao;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by aziza on 03.11.17.
 */

@Entity
@Table(name = "lesson_attachments")
public class LessonAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mime", columnDefinition = "VARCHAR(255)")
    private String mime;

    @Column(name = "size")
    private Integer size;

    @Column(name = "attachment")
    private Blob attachment;

    @Column(name = "uploadDate")
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public LessonAttachment() {
    }

    public LessonAttachment(String name, String mime, Integer size, Blob attachment, Date uploadDate, Lesson lesson, User user) {
        this.name = name;
        this.mime = mime;
        this.size = size;
        this.attachment = attachment;
        this.uploadDate = uploadDate;
        this.lesson = lesson;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Blob getAttachment() {
        return attachment;
    }

    public void setAttachment(Blob attachment) {
        this.attachment = attachment;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
