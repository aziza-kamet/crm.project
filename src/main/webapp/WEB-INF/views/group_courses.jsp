<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: aziza
  Date: 11.11.17
  Time: 7:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="/groups/${gid}/courses" method="post">
    <div class="modal-body">
        <div class="form-course">
            <label>Курсы</label>
            <c:forEach items="${coursesOut}" var="course">
                <div class="i-checks">
                    <input id="course-out-${course.id}" type="checkbox" name="courses" value="${course.id}" class="checkbox-template">
                    <label for="course-out-${course.id}">${course.name}</label>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-secondary">Закрыть</button>
        <button class="btn btn-primary">Сохранить</button>
    </div>
</form>
