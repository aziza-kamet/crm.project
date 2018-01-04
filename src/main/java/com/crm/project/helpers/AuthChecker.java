package com.crm.project.helpers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by aziza on 09.12.17.
 */
public class AuthChecker {

    public static boolean isAuth(HttpSession session) {
        return session.getAttribute("user") != null || session.getAttribute("role") != null;
    }

    public static boolean isAuth(HttpSession session, HttpServletResponse response) throws IOException {

        if (session.getAttribute("user") != null || session.getAttribute("role") != null) {
            return true;
        }

        session.invalidate();
        response.sendRedirect("/");
        return false;
    }

}
