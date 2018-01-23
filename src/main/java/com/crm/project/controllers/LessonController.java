package com.crm.project.controllers;

import com.crm.project.beans.*;
import com.crm.project.dao.*;
import com.crm.project.helpers.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    LessonAttachmentBean attachmentBean;
    @Autowired
    AttendanceBean attendanceBean;

    @PostMapping("/courses/{cid}/lessons")
    public void create(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable(name = "cid") Long cid,
                        @RequestParam(name = "title") String title,
                        @RequestParam(name = "content") String content) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!user.is("admin")) {
            response.sendRedirect("403");
            return;
        }

        lessonBean.create(title, content, cid);
        response.sendRedirect("/courses/" + cid + "/lessons");
    }

    @GetMapping("/courses/{cid}/lessons")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "cid") Long cid) throws IOException {

        HttpSession session = request.getSession();
        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        ModelAndView mv = new ModelAndView("lessons");
        Course course = courseBean.getBy(cid);
        User user = (User) request.getSession().getAttribute("user");

        if ((!user.is("admin"))
                || !userBean.hasCourse(user, cid)) {
            response.sendRedirect("/403");
            return null;
        }

        if(user.is("teacher")) {
            mv.addObject("groups", courseBean.groups(course, user));
        }

        mv.addObject("lessons", courseBean.getLessons(course));
        mv.addObject("cid", cid);
        mv.addObject("course", course);
        return mv;
    }

    @GetMapping("/courses/{cid}/lessons/{lid}")
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "cid") Long cid,
                              @PathVariable(name = "lid") Long lid) throws IOException {

        HttpSession session = request.getSession();
        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        ModelAndView mv = new ModelAndView("lesson");
        Course course = courseBean.getBy(cid);
        Lesson lesson = lessonBean.getBy(lid);
        User user = (User) request.getSession().getAttribute("user");
        List<LessonAttachment> attachments = attachmentBean.getList(lid);

        if (!userBean.hasCourse(user, cid)) {
            response.sendRedirect("/403");
            return null;
        }

        try {
            if (!lesson.getCourse().equals(course)) {
                response.sendRedirect("/403");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/403");
            return null;
        }

        mv.addObject("attachments", attachments);
        mv.addObject("groups", courseBean.groups(course, user));
        mv.addObject("lesson", lessonBean.getBy(lid));
        mv.addObject("cid", cid);
        return mv;
    }

    @PostMapping("courses/{cid}/lessons/{lid}")
    public void delete(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @PathVariable(name = "cid") Long cid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        lessonBean.delete(lid);
        response.sendRedirect("/courses/" + cid + "/lessons");
    }

    @PostMapping("courses/{cid}/lessons/{lid}/edit")
    public void update(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @PathVariable(name = "cid") Long cid,
                       @RequestParam(name = "title") String title,
                       @RequestParam(name = "content") String content) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        lessonBean.update(lid, title, content);
        response.sendRedirect("/courses/" + cid + "/lessons");
    }

    @GetMapping("/lessons/{lid}/groups/{gid}/grades")
    public ModelAndView grades(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "lid") Long lid,
                              @PathVariable(name = "gid") Long gid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!user.is("teacher")) {
            response.sendRedirect("/403");
            return null;
        }
        if (!userBean.hasGroup(user, groupBean.getBy(gid))) {
            response.sendRedirect("/403");
            return null;
        }
        ModelAndView mv = new ModelAndView("lesson_grades");
        Lesson lesson = lessonBean.getBy(lid);
        Group group = groupBean.getBy(gid);

        mv.addObject("lesson", lesson);
        mv.addObject("group", group);
        mv.addObject("students", groupBean.students(group));
        mv.addObject("marks", markBean.getMap(lesson));
        mv.addObject("attendances", attendanceBean.getMap(lesson));
        mv.addObject("avgMarks", lessonBean.avgMarks(lesson));
        mv.addObject("avgAttendances", lessonBean.avgAttendances(lesson));
        return mv;
    }

    @GetMapping("/lessons/{lid}/groups/{gid}/student/{sid}")
    public ModelAndView studentGrades(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "lid") Long lid,
                              @PathVariable(name = "gid") Long gid,
                              @PathVariable(name = "sid") Long sid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!user.is("teacher")) {
            response.sendRedirect("/403");
            return null;
        }
        if (!userBean.hasGroup(user, groupBean.getBy(gid))) {
            response.sendRedirect("/403");
            return null;
        }

        ModelAndView mv = new ModelAndView("student_lesson_grades");
        Lesson lesson = lessonBean.getBy(lid);
        Group group = groupBean.getBy(gid);
        User student = userBean.getBy(sid);

        mv.addObject("lesson", lesson);
        mv.addObject("group", group);
        mv.addObject("marks", markBean.getBy(lesson, student));
        mv.addObject("attendances", attendanceBean.getBy(lesson, student));
        return mv;
    }

    @PostMapping("/lessons/{lid}/groups/{gid}/students/{sid}")
    public void setGrade(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @PathVariable(name = "gid") Long gid,
                       @PathVariable(name = "sid") Long sid,
                       @RequestParam(name = "grade") String grade,
                       @RequestParam(name = "note") String note) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

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

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        try {
            Integer attendanceInt = attendance != null ? 1 : 0;
            Lesson lesson = lessonBean.getBy(lid);
            User user = userBean.getBy(sid);
            attendanceBean.create(lesson, user, attendanceInt, note);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lessons/" + lid + "/groups/" + gid + "/grades");
    }
}
