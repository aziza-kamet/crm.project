package com.crm.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PageController {
    @GetMapping("/403")
    public ModelAndView auth(HttpServletRequest request, HttpServletResponse response) {

        return new ModelAndView("errors/403");
    }

}
