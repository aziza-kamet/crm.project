package com.crm.project.controllers;

import com.crm.project.beans.GroupBean;
import com.crm.project.beans.ScheduleBean;
import com.crm.project.dao.Schedule;
import com.crm.project.enums.Days;
import com.crm.project.helpers.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ModelAndView mv = new ModelAndView("group_schedule");
        mv.addObject("schedule", scheduleMap);
        mv.addObject("days", days);
        mv.addObject("hours", hours);
        return mv;
    }

}
