package com.crm.project.controllers;

import com.crm.project.beans.CompanyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aziza on 11.11.17.
 */

@Controller
public class CompanyController {

    @Autowired
    CompanyBean companyBean;

    @GetMapping("/superadmin/companies")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("superadmin/companies");
        mv.addObject("companies", companyBean.getList());
        return mv;
    }

    @PostMapping("/superadmin/companies")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "description") String description) throws IOException {
        companyBean.create(name, description);
        response.sendRedirect("/superadmin/companies");
    }

    @PostMapping("/superadmin/companies/{id}")
    public void delete(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {
        companyBean.delete(id);
        response.sendRedirect("/superadmin/companies");
    }

    @PostMapping("/superadmin/companies/{id}/edit")
    public void update(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "description") String description) throws IOException {
        companyBean.update(id, name, description);
        response.sendRedirect("/superadmin/companies");
    }
}
