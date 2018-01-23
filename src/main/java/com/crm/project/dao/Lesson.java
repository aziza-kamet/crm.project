package com.crm.project.dao;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aziza on 03.11.17.
 */

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "post_date", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date postDate;

    @Column(name = "active", insertable = false, columnDefinition = "INT(1) DEFAULT '1'")
    private Integer active;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private List<LessonAttachment> attachments;

    public Lesson() {
    }

    public Lesson(String title, String content, Course course) {
        this.title = title;
        this.content = content;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<LessonAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<LessonAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getFormattedPostDate() {

        DateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        return format.format(this.postDate);
    }
}
