package com.crm.project.controllers;

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
    @GetMapping("/")
    public ModelAndView auth(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null || session.getAttribute("role") == null) {
            session.invalidate();
            return new ModelAndView("auth");
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
        request.getSession().invalidate();
        response.sendRedirect("/");
    }
}
