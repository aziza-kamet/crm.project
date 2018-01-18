package com.crm.project.controllers;

import com.crm.project.beans.CompanyBean;
import com.crm.project.beans.CourseBean;
import com.crm.project.beans.GroupBean;
import com.crm.project.beans.UserBean;
import com.crm.project.dao.Company;
import com.crm.project.dao.Group;
import com.crm.project.dao.User;
import com.crm.project.helpers.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziza on 29.11.17.
 */
@Controller
public class GroupController {
    @Autowired
    GroupBean groupBean;
    @Autowired
    CompanyBean companyBean;
    @Autowired
    UserBean userBean;
    @Autowired
    CourseBean courseBean;

    @PostMapping("/groups")
    public void create(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(name = "name") String name) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        groupBean.create(name, user.getCompany().getId());
        response.sendRedirect("/groups");
    }

    @GetMapping("/groups")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("admin")) {
            response.sendRedirect("403");
            return null;
        }
        ModelAndView mv = new ModelAndView("groups");
        Company company = companyBean.getBy(user.getCompany().getId());
        mv.addObject("groups", company.getGroups());
        return mv;
    }

    @PostMapping("/groups/{id}/edit")
    public void update(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id,
                       @RequestParam(name = "name") String name) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        groupBean.update(id, name);
        response.sendRedirect("/groups");
    }

    @PostMapping("/groups/{id}")
    public void delete(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        groupBean.delete(id);
        response.sendRedirect("/groups");
    }

    @GetMapping("/groups/{id}/students")
    public ModelAndView students(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        ModelAndView mv = new ModelAndView("group_students");
        User user = (User) request.getSession().getAttribute("user");
        Group group = groupBean.getBy(id);

        mv.addObject("students", groupBean.students(group));
        mv.addObject("studentsOut", groupBean.usersOut(user.getCompany(), group, "student"));
        mv.addObject("gid", id);
        return mv;
    }

    @GetMapping("/groups/{id}/teachers")
    public ModelAndView teachers(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        ModelAndView mv = new ModelAndView("group_teachers");
        User user = (User) request.getSession().getAttribute("user");
        Group group = groupBean.getBy(id);
        Company company = companyBean.getBy(user.getCompany().getId());

        mv.addObject("teachers", groupBean.teachers(group));
        mv.addObject("teachersOut", groupBean.usersOut(company, group, "teacher"));
        mv.addObject("gid", id);
        return mv;
    }

    @PostMapping("/groups/{id}/students")
    public void addStudents(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id,
                            @RequestParam(name = "students") String[] students) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        Group group = groupBean.getBy(id);
        for (String sid:
             students) {
            try {
                group.getUsers().add(userBean.getBy(Long.parseLong(sid)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        groupBean.update(group);

        response.sendRedirect("/groups/" + id + "/students");
    }

    @PostMapping("/groups/{gid}/students/{sid}")
    public void deleteStudents(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "gid") Long gid,
                            @PathVariable(name = "sid") Long sid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        Group group = groupBean.getBy(gid);
        group.getUsers().remove(userBean.getBy(sid));
        groupBean.update(group);

        response.sendRedirect("/groups/" + gid + "/students");
    }

    @PostMapping("/groups/{id}/teachers")
    public void addTeachers(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id,
                            @RequestParam(name = "teachers") String[] teachers) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        Group group = groupBean.getBy(id);
        for (String tid:
                teachers) {
            try {
                group.getUsers().add(userBean.getBy(Long.parseLong(tid)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        groupBean.update(group);

        response.sendRedirect("/groups/" + id + "/teachers");
    }

    @PostMapping("/groups/{gid}/teachers/{tid}")
    public void deleteTeachers(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable(name = "gid") Long gid,
                               @PathVariable(name = "tid") Long tid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        Group group = groupBean.getBy(gid);
        group.getUsers().remove(userBean.getBy(tid));
        groupBean.update(group);

        response.sendRedirect("/groups/" + gid + "/teachers");
    }

    @GetMapping("/groups/{id}/courses")
    public ModelAndView courses(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        ModelAndView mv = new ModelAndView("group_courses");
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("admin")) {
            response.sendRedirect("403");
            return null;
        }
        Group group = groupBean.getBy(id);
        Company company = companyBean.getBy(user.getCompany().getId());

        mv.addObject("courses", groupBean.courses(group));
        mv.addObject("coursesOut", groupBean.coursesOut(company, group));
        mv.addObject("gid", id);
        return mv;
    }

    @GetMapping("/groups/{id}/courses/out")
    public @ResponseBody ModelAndView coursesOut(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id) throws IOException {

        ModelAndView mv = new ModelAndView("group_courses");
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("admin")) {
            response.sendRedirect("403");
            return null;
        }
        Group group = groupBean.getBy(id);
        Company company = companyBean.getBy(user.getCompany().getId());

        mv.addObject("coursesOut", groupBean.coursesOut(company, group));
        mv.addObject("gid", id);
        return mv;
    }

    @PostMapping("/groups/{id}/courses")
    public void addCourses(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id,
                            @RequestParam(name = "courses") String[] courses) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        Group group = groupBean.getBy(id);
        for (String tid:
                courses) {
            try {
                group.getCourses().add(courseBean.getBy(Long.parseLong(tid)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        groupBean.update(group);

        response.sendRedirect("/groups");
    }

    @PostMapping("/groups/{gid}/courses/{cid}")
    public void deleteCourses(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable(name = "gid") Long gid,
                               @PathVariable(name = "cid") Long cid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        Group group = groupBean.getBy(gid);
        group.getCourses().remove(courseBean.getBy(cid));
        groupBean.update(group);

        response.sendRedirect("/groups");
    }

    @GetMapping("/my_groups")
    public ModelAndView my(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole().getName().equals("admin")) {
            response.sendRedirect("403");
            return null;
        }
        ModelAndView mv = new ModelAndView("groups");
        mv.addObject("groups", user.getGroups());
        return mv;
    }
}
