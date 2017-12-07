package com.crm.project.dao;

import javax.persistence.*;

/**
 * Created by aziza on 03.11.17.
 */

@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "mark_value")
    private Integer markValue;

    @Column(name = "mark_notes")
    private String markNotes;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Mark() {
    }

    public Mark(Integer markValue, String markNotes, Lesson lesson, User user) {
        this.markValue = markValue;
        this.markNotes = markNotes;
        this.lesson = lesson;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMarkValue() {
        return markValue;
    }

    public void setMarkValue(Integer markValue) {
        this.markValue = markValue;
    }

    public String getMarkNotes() {
        return markNotes;
    }

    public void setMarkNotes(String markNotes) {
        this.markNotes = markNotes;
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
