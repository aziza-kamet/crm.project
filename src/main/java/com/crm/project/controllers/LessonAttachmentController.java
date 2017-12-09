package com.crm.project.controllers;

import com.crm.project.beans.LessonAttachmentBean;
import com.crm.project.dao.LessonAttachment;
import com.crm.project.dao.User;
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
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;

/**
 * Created by aziza on 10.11.17.
 */
@Controller
public class LessonAttachmentController {
    @Autowired
    LessonAttachmentBean attachmentBean;

    @GetMapping("/lessons/{lid}/attachments")
    public ModelAndView auth(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable(name = "lid") Long lid) {

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

        try {
            User user = (User) request.getSession().getAttribute("user");
            attachmentBean.create(name, file, lid, user);
        } catch (MaxUploadSizeExceededException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lessons/" + lid + "/attachments");
    }

    @PostMapping("/attachments/{id}/download")
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable(name = "id") Long id) throws IOException {

        LessonAttachment attachment = attachmentBean.getBy(id);
        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" + attachment.getName() + "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType(attachment.getMime());
            IOUtils.copy(attachment.getAttachment().getBinaryStream(), out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/lessons/" + attachment.getLesson().getId() + "/attachments");
    }

    @PostMapping("/attachments/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable(name = "id") Long id) throws IOException {

        LessonAttachment attachment = attachmentBean.getBy(id);
        attachmentBean.delete(id);
        response.sendRedirect("/lessons/" + attachment.getLesson().getId() + "/attachments");
    }
}