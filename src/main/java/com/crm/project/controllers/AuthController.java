package com.crm.project.controllers;

import com.crm.project.helpers.AuthChecker;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class AuthController {
    @GetMapping("/profile")
    public ModelAndView auth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        if (!AuthChecker.isAuth(session, response)) {
            return null;
        }

        return new ModelAndView("profile");
    }

    @GetMapping("/superadmin")
    public ModelAndView reg(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null || session.getAttribute("role") == null) {
            session.invalidate();
            return new ModelAndView("superadmin/auth");
        }

        return new ModelAndView("superadmin/profile");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }
        request.getSession().invalidate();
        response.sendRedirect("/");
    }
}
