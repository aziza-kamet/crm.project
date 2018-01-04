package com.crm.project.controllers;

import com.crm.project.beans.MarkBean;
import com.crm.project.dao.User;
import com.crm.project.helpers.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class MarkController {
    @Autowired
    MarkBean markBean;

    @GetMapping("/my_grades")
    public ModelAndView my(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("student")) {
            response.sendRedirect("403");
            return null;
        }
        ModelAndView mv = new ModelAndView("my_grades");
        ArrayList<Object[]> lessons = markBean.avgAndTotal(user);
        mv.addObject("lessons", lessons);

        return mv;
    }

}
