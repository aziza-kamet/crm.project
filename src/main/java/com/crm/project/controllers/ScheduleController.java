package com.crm.project.controllers;

import com.crm.project.beans.CourseBean;
import com.crm.project.beans.GroupBean;
import com.crm.project.beans.ScheduleBean;
import com.crm.project.dao.Course;
import com.crm.project.dao.Group;
import com.crm.project.dao.Schedule;
import com.crm.project.enums.Days;
import com.crm.project.helpers.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class ScheduleController {
    @Autowired
    ScheduleBean scheduleBean;
    @Autowired
    GroupBean groupBean;
    @Autowired
    CourseBean courseBean;
    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/groups/{gid}/schedule")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable(name = "gid") Long gid) throws IOException {
        HttpSession session = request.getSession();
        if (!AuthChecker.isAuth(session)) {
            session.invalidate();
            return new ModelAndView("auth");
        }

        List<Schedule> scheduleList = scheduleBean.getListByGroup(groupBean.getBy(gid));
        HashMap<String, HashMap<String, Schedule>> scheduleMap = new HashMap<String, HashMap<String, Schedule>>();

        HashMap days = (HashMap) applicationContext.getBean("DAYS");
        HashMap hours = (HashMap) applicationContext.getBean("HOURS");

        for (Schedule schedule:
             scheduleList) {
            HashMap<String, Schedule> scheduleByHour = new HashMap<String, Schedule>();
            Integer hourId = schedule.getHourId();
            Integer dayId = schedule.getDayId();
            if (scheduleMap.containsKey(hourId)) {
                scheduleByHour = scheduleMap.get(hourId);
            }
            scheduleByHour.put(dayId + "", schedule);
            scheduleMap.put(hourId + "", scheduleByHour);
        }

        Group group = groupBean.getBy(gid);
        ModelAndView mv = new ModelAndView("group_schedule");
        mv.addObject("schedule", scheduleMap);
        mv.addObject("days", days);
        mv.addObject("hours", hours);
        mv.addObject("gid", gid);
        mv.addObject("courses", group.getCourses());
        return mv;
    }

    @PostMapping("/groups/{gid}/schedule")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "gid") Long gid,
                       @RequestParam(name = "course") String courseId,
                       @RequestParam(name = "day") String dayId,
                       @RequestParam(name = "hour") String hourId,
                       @RequestParam(name = "startDay") String startDay,
                       @RequestParam(name = "endDay") String endDay,
                       @RequestParam(name = "notes", required = false) String notes) throws IOException {
        try {
            Long cid = Long.parseLong(courseId);
            Integer did = Integer.parseInt(dayId);
            Integer hid = Integer.parseInt(hourId);

            Group group = groupBean.getBy(gid);
            Course course = courseBean.getBy(cid);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = format.parse(startDay);
            Date endDate = format.parse(endDay);

            scheduleBean.create(group, course, did, hid, startDate, endDate, notes);
        } catch (NumberFormatException e) {
//            TODO return error message
            e.printStackTrace();
        } catch (ParseException e) {
//            TODO return error message
            e.printStackTrace();
        }

        response.sendRedirect("/groups/" + gid + "/schedule");
    }

}
