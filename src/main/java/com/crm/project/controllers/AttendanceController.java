package com.crm.project.controllers;

import com.crm.project.beans.AttendanceBean;
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
public class AttendanceController {
    @Autowired
    AttendanceBean attendanceBean;

}
