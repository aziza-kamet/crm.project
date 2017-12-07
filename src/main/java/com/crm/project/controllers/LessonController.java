package com.crm.project.controllers;

import com.crm.project.beans.*;
import com.crm.project.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aziza on 29.11.17.
 */
@Controller
public class LessonController {
    @Autowired
    LessonBean lessonBean;
    @Autowired
    CourseBean courseBean;
    @Autowired
    GroupBean groupBean;
    @Autowired
    UserBean userBean;
    @Autowired
    MarkBean markBean;
    @Autowired
    AttendanceBean attendanceBean;

    @PostMapping("/courses/{cid}/lessons")
    public void create(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable(name = "cid") Long cid,
                        @RequestParam(name = "title") String title,
                        @RequestParam(name = "content") String content) throws IOException {
        lessonBean.create(title, content, cid);
        response.sendRedirect("/courses/" + cid + "/lessons");
    }

    @GetMapping("/courses/{cid}/lessons")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "cid") Long cid) throws IOException {
        ModelAndView mv = new ModelAndView("lessons");
        Course course = courseBean.getBy(cid);
        User user = (User) request.getSession().getAttribute("user");

        if (!userBean.hasCourse(user, cid)) {
            response.sendRedirect("403");
            return null;
        }

        ArrayList<Lesson> lessons = new ArrayList<Lesson>(course.getLessons());

        if(user.getRole().getName().equals("teacher")) {
            mv.addObject("groups", courseBean.groups(course, user));
        }

        mv.addObject("lessons", course.getLessons());
        mv.addObject("cid", cid);
        return mv;
    }

    @PostMapping("courses/{cid}/lessons/{lid}/edit")
    public void update(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @PathVariable(name = "cid") Long cid,
                       @RequestParam(name = "title") String title,
                       @RequestParam(name = "content") String content) throws IOException {
        lessonBean.update(lid, title, content);
        response.sendRedirect("/courses/" + cid + "/lessons");
    }

    @GetMapping("/lessons/{lid}/groups/{gid}/grades")
    public ModelAndView grades(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "lid") Long lid,
                              @PathVariable(name = "gid") Long gid) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("teacher")) {
            response.sendRedirect("403");
            return null;
        }
        ModelAndView mv = new ModelAndView("lesson_grades");
        Lesson lesson = lessonBean.getBy(lid);
        Group group = groupBean.getBy(gid);

        mv.addObject("lesson", lesson);
        mv.addObject("group", group);
        mv.addObject("students", groupBean.students(group));
        mv.addObject("marks", markBean.getList(lesson));
        return mv;
    }

    @GetMapping("/lessons/{lid}/groups/{gid}/attendances")
    public ModelAndView attendances(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "lid") Long lid,
                              @PathVariable(name = "gid") Long gid) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("teacher")) {
//            TODO redirect to proper page
            response.sendRedirect("/403");
            return null;
        }
        ModelAndView mv = new ModelAndView("lesson_attendances");
        Lesson lesson = lessonBean.getBy(lid);
        Group group = groupBean.getBy(gid);

        System.out.println(attendanceBean.getList(lesson));
        mv.addObject("lesson", lesson);
        mv.addObject("group", group);
        mv.addObject("students", groupBean.students(group));
        mv.addObject("attendances", attendanceBean.getList(lesson));
        return mv;
    }

    @PostMapping("/lessons/{lid}/groups/{gid}/students/{sid}")
    public void setGrade(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @PathVariable(name = "gid") Long gid,
                       @PathVariable(name = "sid") Long sid,
                       @RequestParam(name = "grade") String grade,
                       @RequestParam(name = "note") String note) throws IOException {
        try {
            Integer gradeInt = Integer.parseInt(grade);
            Lesson lesson = lessonBean.getBy(lid);
            User user = userBean.getBy(sid);
            markBean.create(lesson, user, gradeInt, note);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lessons/" + lid + "/groups/" + gid + "/grades");
    }

    @PostMapping("/lessons/{lid}/groups/{gid}/students/{sid}/attendances")
    public void setAttendance(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @PathVariable(name = "gid") Long gid,
                       @PathVariable(name = "sid") Long sid,
                       @RequestParam(name = "attendance", required = false) String attendance,
                       @RequestParam(name = "note") String note) throws IOException {

        try {
            Integer attendanceInt = attendance != null ? 1 : 0;
            Lesson lesson = lessonBean.getBy(lid);
            User user = userBean.getBy(sid);
            attendanceBean.create(lesson, user, attendanceInt, note);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lessons/" + lid + "/groups/" + gid + "/attendances");
    }
}
