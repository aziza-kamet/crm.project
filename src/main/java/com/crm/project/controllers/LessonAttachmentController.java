package com.crm.project.controllers;

import com.crm.project.beans.LessonAttachmentBean;
import com.crm.project.beans.LessonBean;
import com.crm.project.beans.UserBean;
import com.crm.project.dao.Lesson;
import com.crm.project.dao.LessonAttachment;
import com.crm.project.dao.User;
import com.crm.project.helpers.AuthChecker;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class LessonAttachmentController {
    @Autowired
    LessonAttachmentBean attachmentBean;
    @Autowired
    LessonBean lessonBean;
    @Autowired
    UserBean userBean;

    @GetMapping("/lessons/{lid}/attachments")
    public ModelAndView auth(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable(name = "lid") Long lid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return null;
        }

        ModelAndView mv = new ModelAndView("lesson_attachments");
        List<LessonAttachment> attachments = attachmentBean.getList(lid);
        mv.addObject("attachments", attachments);
        mv.addObject("lid", lid);
        return mv;
    }

    @PostMapping("/lessons/{lid}/attachments")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "lid") Long lid,
                       @RequestParam(name = "name", required = false) String name,
                       @RequestParam(name = "file") MultipartFile file) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        Lesson lesson = lessonBean.getBy(lid);

        if (!userBean.hasCourse(user, lesson.getCourse().getId())) {
            response.sendRedirect("/403");
            return;
        }

        if (!file.isEmpty()) {

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if (!Arrays.asList(attachmentBean.getExtensions()).contains(extension)) {
                response.sendRedirect("/courses/" + lesson.getCourse().getId() + "/lessons/" + lid);
                return;
            }

            if (name.equals("")) {
                name = file.getOriginalFilename();
            } else {
                name = name + "." + extension;
            }

            try {
                byte[] bytes = file.getBytes();

                String rootPath = request.getSession().getServletContext().getRealPath("/resources/images/attachments");
                File dir = new File(rootPath + File.separator + "lesson_" + lid);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                attachmentBean.create(name, lesson, user);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            TODO show error message
        }

        response.sendRedirect("/courses/" + lesson.getCourse().getId() + "/lessons/" + lid);
    }

    @GetMapping("lessons/{lid}/attachments/{aid}")
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable(name = "lid") Long lid,
                         @PathVariable(name = "aid") Long aid) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        Lesson lesson = lessonBean.getBy(lid);
        LessonAttachment attachment = attachmentBean.getBy(aid);

        if (!userBean.hasCourse(user, lesson.getCourse().getId())
                || !lessonBean.hasAttachment(lesson, attachment)) {
            response.sendRedirect("/403");
            return;
        }


        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(
                    new File(request.getSession().getServletContext().getRealPath("/resources/images/attachments")
                            + File.separator + "lesson_" + lid + File.separator + attachment.getName())
            ));
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @PostMapping("/attachments/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {

        if (!AuthChecker.isAuth(request.getSession(), response)) {
            return;
        }

        LessonAttachment attachment = attachmentBean.getBy(id);
        Lesson lesson = attachment.getLesson();

        attachmentBean.delete(id);
        response.sendRedirect("/courses/" + lesson.getCourse().getId() + "/lessons/" + lesson.getId());
    }
}
