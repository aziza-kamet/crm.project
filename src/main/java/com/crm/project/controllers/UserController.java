package com.crm.project.controllers;

import com.crm.project.beans.CompanyBean;
import com.crm.project.beans.RoleBean;
import com.crm.project.beans.UserBean;
import com.crm.project.dao.User;
import com.crm.project.helpers.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aziza on 10.11.17.
 */

@Controller
public class UserController {

    @Autowired
    UserBean userBean;
    @Autowired
    CompanyBean companyBean;
    @Autowired
    RoleBean roleBean;

    @PostMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(name = "login") String login,
                     @RequestParam(name = "password") String password) throws IOException {
        User user = userBean.getBy(login, password);
        if (user == null) {
            response.sendRedirect("/");
            return;
        }

        request.getSession().setAttribute("role", user.getRole().getName());
        request.getSession().setAttribute("user", user);
        response.sendRedirect("/");
    }

    @PostMapping("/edit")
    public void updateLogin(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name = "login") String login,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "surname") String surname) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
//        TODO check if such login exists
        userBean.update(user.getId(), login, name, surname, user.getRole().getId(), user.getCompany().getId());
        request.getSession().setAttribute("user", userBean.getBy(user.getId()));
        response.sendRedirect("/");
    }

    @PostMapping("/password")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "new-password") String newPassword,
                               @RequestParam(name = "conf-password") String confPassword) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        if (userBean.getBy(user.getLogin(), password) == null) {
            response.sendRedirect("/");
            return;
        }

        if (!newPassword.equals(confPassword)) {
            response.sendRedirect("/");
            return;
        }

        userBean.updatePassword(user.getId(), newPassword);
        request.getSession().setAttribute("user", userBean.getBy(user.getId()));
        response.sendRedirect("/");
    }

    @GetMapping("/teachers")
    public ModelAndView teachers(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }


        User user = (User) request.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView("teachers");
        mv.addObject("users", userBean.getListOfTeachers(user.getCompany().getId()));
        return mv;
    }

    @PostMapping("/teachers")
    public void createTeacher(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name = "login") String login,
                            @RequestParam(name = "password") String password,
                            @RequestParam(name = "conf-password") String confPassword,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "surname") String surname) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        if (!password.equals(confPassword)) {

            response.sendRedirect("/teachers");
            return;
        }

        try {
            userBean.create(login, password, name, surname, roleBean.getBy("teacher").getId(), user.getCompany().getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/teachers");
    }

    @PostMapping("/teachers/{id}")
    public void deleteTeacher(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }


        userBean.delete(id);

        response.sendRedirect("/teachers");
    }

    @PostMapping("/teachers/{id}/edit")
    public void updateTeacher(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id,
                            @RequestParam(name = "login") String login,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "surname") String surname) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        try {
            userBean.update(id, login, name, surname, roleBean.getBy("teacher").getId(), user.getCompany().getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/teachers");
    }

    @GetMapping("/students")
    public ModelAndView students(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }


        User user = (User) request.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView("students");
        mv.addObject("users", userBean.getListOfStudents(user.getCompany().getId()));
        return mv;
    }

    @PostMapping("/students")
    public void createStudent(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(name = "login") String login,
                              @RequestParam(name = "password") String password,
                              @RequestParam(name = "conf-password") String confPassword,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "surname") String surname) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        if (!password.equals(confPassword)) {
            response.sendRedirect("/students");
            return;
        }

        try {
            userBean.create(login, password, name, surname, roleBean.getBy("student").getId(), user.getCompany().getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/students");
    }

    @PostMapping("/students/{id}")
    public void deleteStudents(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }


        userBean.delete(id);

        response.sendRedirect("/students");
    }

    @PostMapping("/students/{id}/edit")
    public void updateStudents(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "id") Long id,
                              @RequestParam(name = "login") String login,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "surname") String surname) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        try {
            userBean.update(id, login, name, surname, roleBean.getBy("student").getId(), user.getCompany().getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/students");
    }


    @GetMapping("/superadmin/admins")
    public ModelAndView admins() {
        ModelAndView mv = new ModelAndView("superadmin/admins");
        mv.addObject("users", userBean.getListOfAdmins());
        mv.addObject("companies", companyBean.getList());
        return mv;
    }

    @PostMapping("/superadmin/admins")
    public void createAdmin(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(name = "login") String login,
                       @RequestParam(name = "password") String password,
                       @RequestParam(name = "conf-password") String confPassword,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "surname") String surname,
                       @RequestParam(name = "company") String cid) throws IOException {

        if (!password.equals(confPassword)) {
            response.sendRedirect("/superadmin/admins");
            return;
        }

        try {
            userBean.create(login, password, name, surname, roleBean.getBy("admin").getId(), Long.parseLong(cid));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/superadmin/admins");
    }

    @PostMapping("/superadmin/users/{id}")
    public void deleteAdmin(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {

        userBean.delete(id);

        response.sendRedirect("/superadmin/admins");
    }

    @PostMapping("/superadmin/admins/{id}/edit")
    public void updateAdmin(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable(name = "id") Long id,
                            @RequestParam(name = "login") String login,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "surname") String surname,
                            @RequestParam(name = "company") String cid) throws IOException {

        try {
            userBean.update(id, login, name, surname, roleBean.getBy("admin").getId(), Long.parseLong(cid));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/superadmin/admins");
    }

}
