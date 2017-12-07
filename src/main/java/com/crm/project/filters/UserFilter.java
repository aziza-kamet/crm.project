package com.crm.project.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aziza on 10.11.17.
 */
public class UserFilter implements Filter {

    private HashMap<String, ArrayList<String>> pages = new HashMap<String, ArrayList<String>>();

    public void init(FilterConfig filterConfig) throws ServletException {
        ArrayList<String> adminPages = new ArrayList<String>();
        adminPages.add("/teachers");
        adminPages.add("/students");
        pages.put("admin", adminPages);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        if (!request.getRequestURI().equals("/") && !request.getRequestURI().equals("/auth")) {
//            if (request.getSession().getAttribute("user") == null
//                    || request.getSession().getAttribute("role") == null) {
//                request.getSession().invalidate();
//                response.sendRedirect("/");
//                return;
//            }
//        }
//
//        if (request.getSession().getAttribute("role") != null) {
//            String role = (String) request.getSession().getAttribute("role");
//            boolean hasAccess = false;
//            if (!pages.containsKey(role) || !pages.get(role).contains(request.getRequestURI())) {
//                response.sendRedirect("/403");
//                return;
//            }
//        }

        chain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
