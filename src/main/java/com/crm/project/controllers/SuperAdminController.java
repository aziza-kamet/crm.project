package com.crm.project.controllers;

import com.crm.project.beans.SuperAdminBean;
import com.crm.project.dao.SuperAdmin;
import com.crm.project.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by aziza on 10.11.17.
 */

@Controller
public class SuperAdminController {

    @Autowired
    SuperAdminBean adminBean;

    @PostMapping("/superadmin/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(name = "login") String login,
                     @RequestParam(name = "password") String password) throws IOException {
        SuperAdmin superAdmin = adminBean.getBy(login, password);
        if (superAdmin == null) {
            response.sendRedirect("/superadmin");
            return;
        }

//        TODO get role from final string
        request.getSession().setAttribute("role", "superadmin");
        request.getSession().setAttribute("user", superAdmin);
        response.sendRedirect("/superadmin");
    }

    @PostMapping("/superadmin/login")
    public void updateLogin(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(name = "login") String login) throws IOException {
        SuperAdmin superAdmin = (SuperAdmin) request.getSession().getAttribute("user");
//        TODO check if such login exists
        adminBean.update(superAdmin.getId(), login);
        request.getSession().setAttribute("user", adminBean.getBy(superAdmin.getId()));
        response.sendRedirect("/superadmin");
    }

    @PostMapping("/superadmin/password")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(name = "password") String password,
                     @RequestParam(name = "new-password") String newPassword,
                     @RequestParam(name = "conf-password") String confPassword) throws IOException {
        SuperAdmin superAdmin = (SuperAdmin) request.getSession().getAttribute("user");

        if (adminBean.getBy(superAdmin.getLogin(), password) == null) {
            response.sendRedirect("/superadmin");
            return;
        }

        if (!newPassword.equals(confPassword)) {
            response.sendRedirect("/superadmin");
            return;
        }

        adminBean.updatePassword(superAdmin.getId(), newPassword);
        request.getSession().setAttribute("user", adminBean.getBy(superAdmin.getId()));
        response.sendRedirect("/superadmin");
    }

    @GetMapping("/superadmin/superadmins")
    public ModelAndView reg(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("superadmin/superadmins");
        mv.addObject("admins", adminBean.getList());
        return mv;
    }

    @PostMapping("/superadmin/superadmins")
    public void create(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(name = "login") String login,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "conf-password") String confPassword) throws IOException {

        if (!password.equals(confPassword)) {
            response.sendRedirect("/superadmin/superadmins");
            return;
        }

//      TODO check if login exists
        adminBean.create(login, password);
        response.sendRedirect("/superadmin/superadmins");
    }

    @PostMapping("/superadmin/superadmins/{id}")
    public void delete(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable(name = "id") Long id) throws IOException {

//      TODO check if login exists
        adminBean.delete(id);
        response.sendRedirect("/superadmin/superadmins");
    }
}
