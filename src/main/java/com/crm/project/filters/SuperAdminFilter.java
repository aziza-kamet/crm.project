package com.crm.project.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aziza on 17.11.17.
 */
public class SuperAdminFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (!request.getRequestURI().equals("/superadmin") && !request.getRequestURI().equals("/superadmin/auth")) {
            if (request.getSession().getAttribute("user") == null
                    || request.getSession().getAttribute("role") == null) {
                request.getSession().invalidate();
                response.sendRedirect("/superadmin");
                return;
            }
        }

        if (request.getSession().getAttribute("role") != null
                && !request.getSession().getAttribute("role").equals("superadmin")) {
            response.sendRedirect("/403");
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
