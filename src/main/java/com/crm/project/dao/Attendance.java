package com.crm.project.dao;

import javax.persistence.*;

/**
 * Created by aziza on 03.11.17.
 */
@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "attendance_value")
    private Integer attendanceValue;

    @Column(name = "attendance_note")
    private String attendanceNote;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Attendance() {
    }

    public Attendance(Integer attendanceValue, String attendanceNote, Lesson lesson, User user) {
        this.attendanceValue = attendanceValue;
        this.attendanceNote = attendanceNote;
        this.lesson = lesson;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttendanceValue() {
        return attendanceValue;
    }

    public void setAttendanceValue(Integer attendanceValue) {
        attendanceValue = attendanceValue;
    }

    public String getAttendanceNote() {
        return attendanceNote;
    }

    public void setAttendanceNote(String attendanceNote) {attendanceNote = attendanceNote;
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
