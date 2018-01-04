package com.crm.project.dao;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aziza on 03.11.17.
 */

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "day_id")
    private Integer dayId;

    @Column(name = "hour_id")
    private Integer hourId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Schedule() {
    }

    public Schedule(Integer dayId, Integer hourId, Date startDate, Date endDate, String notes, Group group, Course course) {
        this.dayId = dayId;
        this.hourId = hourId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.group = group;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public Integer getHourId() {
        return hourId;
    }

    public void setHourId(Integer hourId) {
        this.hourId = hourId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
