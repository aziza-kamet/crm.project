package com.crm.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class NewsController {
    @GetMapping("/dashboard")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("news");
    }
}
