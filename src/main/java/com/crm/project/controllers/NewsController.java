package com.crm.project.controllers;

import com.crm.project.beans.CompanyBean;
import com.crm.project.beans.NewsBean;
import com.crm.project.dao.Company;
import com.crm.project.dao.News;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class NewsController {
    @Autowired
    NewsBean newsBean;

    @PostMapping("/news")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(name = "title") String title,
                       @RequestParam(name = "content") String content) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

//      TODO check if user is admin
        User user = (User) request.getSession().getAttribute("user");
        newsBean.create(user, title, content);
        response.sendRedirect("/");
    }

    @PostMapping("/news/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        newsBean.delete(id);
        response.sendRedirect("/");
    }

    @PostMapping("/news/{id}/edit")
    public void update(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id,
                       @RequestParam(name = "title") String title,
                       @RequestParam(name = "content") String content) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        newsBean.update(id, title, content);
        response.sendRedirect("/");
    }

    @GetMapping("/")
    public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        if (!AuthChecker.isAuth(session)) {
            session.invalidate();
            return new ModelAndView("auth");
        }

        User user = (User) request.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView("dashboard");
        List<News> news = newsBean.getListOf(user);
        mv.addObject("news", news);
        return mv;
    }
}
