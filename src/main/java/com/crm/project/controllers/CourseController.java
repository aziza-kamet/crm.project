package com.crm.project.controllers;

import com.crm.project.beans.AttendanceBean;
import com.crm.project.beans.CompanyBean;
import com.crm.project.beans.CourseBean;
import com.crm.project.beans.MarkBean;
import com.crm.project.dao.Company;
import com.crm.project.dao.User;
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
import java.io.IOException;

/**
 * Created by aziza on 29.11.17.
 */
@Controller
public class CourseController {
    @Autowired
    CourseBean courseBean;
    @Autowired
    MarkBean markBean;
    @Autowired
    AttendanceBean attendanceBean;
    @Autowired
    CompanyBean companyBean;

    @PostMapping("/courses")
    public void create(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(name = "name") String name,
                        @RequestParam(name = "description") String description) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        courseBean.create(name, description, user.getCompany().getId());
        response.sendRedirect("/courses");
    }

    @GetMapping("/courses")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!user.is("admin")) {
            response.sendRedirect("403");
            return null;
        }
        ModelAndView mv = new ModelAndView("courses");
        Company company = companyBean.getBy(user.getCompany().getId());
        mv.addObject("courses", company.getCourses());
        return mv;
    }

    @GetMapping("/my_courses")
    public ModelAndView my(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user.is("admin")) {
            response.sendRedirect("403");
            return null;
        }
        ModelAndView mv = new ModelAndView("courses");
        mv.addObject("courses", courseBean.my(user));
        if (user.is("student")) {
            mv.addObject("marks", markBean.avgAndTotal(user));
            mv.addObject("attendances", attendanceBean.avgAndTotal(user));
        }
        return mv;
    }

    @PostMapping("/courses/{id}/edit")
    public void update(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "description") String description) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        courseBean.update(id, name, description);
        response.sendRedirect("/courses");
    }

    @PostMapping("/courses/{id}")
    public void delete(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        courseBean.delete(id);
        response.sendRedirect("/courses");
    }
}
