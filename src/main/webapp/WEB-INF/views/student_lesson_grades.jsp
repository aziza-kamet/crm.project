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
<html>
<head>
    <title>CRM | Оценки</title>
    <jsp:include page="partials/header.jsp"/>
</head>
<body>
<div class="page home-page">
    <jsp:include page="partials/menu.jsp"/>
    <div class="page-content d-flex align-items-stretch">
        <jsp:include page="partials/side_menu.jsp"/>

        <tags:inner>
            <!-- Page Header-->
            <header class="page-header">
                <div class="container-fluid">
                    <h2 class="no-margin-bottom">
                        Оценки группы ${group.name} <small>${lesson.title}</small>
                    </h2>
                </div>
            </header>
            <section>
                <div class="container-fluid">
                    <div class="card">
                        <div class="card-body">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Оценки</th>
                                    <th>Посещаемость</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <c:forEach items="${marks}" var="mark">
                                                <span class="badge badge-info badge-lg position-relative">
                                                    <div class="tools-right tools-small">
                                                        <form action="/lessons/${lesson.id}/groups/${group.id}/grades/${mark.id}" method="post">
                                                            <button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>
                                                        </form>
                                                    </div>
                                                    ${mark.markValue}
                                                    <c:if test="${mark.markNotes != ''}">
                                                        (${mark.markNotes})
                                                    </c:if>
                                                </span>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach items="${attendances}" var="attendance">
                                                <span class="badge badge-info badge-lg position-relative">
                                                    <div class="tools-right tools-small">
                                                        <form action="/lessons/${lesson.id}/groups/${group.id}/attendances/${attendance.id}" method="post">
                                                            <button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>
                                                        </form>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${attendance.attendanceValue eq 0}">
                                                            Отсутствовал
                                                        </c:when>
                                                        <c:otherwise>
                                                            Присутствовал
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:if test="${attendance.attendanceNote ne ''}">
                                                        (${attendance.attendanceNote})
                                                    </c:if>
                                                </span>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                        <%--<div class="accordion">--%>
                            <%--<div class="card collapse-card">--%>
                                <%--<div class="card-header" id="header">--%>
                                    <%--<div class="row">--%>
                                        <%--<div class="col-sm-4"><h3>ФИО</h3></div>--%>
                                        <%--<div class="col-sm-3"><h3>Оценка</h3></div>--%>
                                        <%--<div class="col-sm-4"><h3>Посещаемость</h3></div>--%>
                                        <%--<div class="col-sm-1"></div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<c:forEach items="${students}" var="student">--%>
                                <%--<div class="card collapse-card">--%>
                                    <%--<div class="card-header" id="student-${student.id}">--%>
                                        <%--<div class="row mb-0">--%>
                                            <%--<div class="col-sm-4">--%>
                                                <%--${student.name} ${student.surname}--%>
                                            <%--</div>--%>
                                            <%--<div class="col-sm-3">--%>
                                                <%--${avgMarks.get(student.id)}--%>
                                                <%--<a href="#" data-toggle="modal" data-target="#add-modal-${student.id}"><i class="fa fa-plus"></i></a>--%>
                                            <%--</div>--%>
                                            <%--<div class="col-sm-4">--%>
                                                <%--<c:if test="${avgAttendances.get(student.id) ne null}">--%>
                                                    <%--${avgAttendances.get(student.id)}%--%>
                                                <%--</c:if>--%>
                                                <%--<a href="#" data-toggle="modal" data-target="#add-att-modal-${student.id}"><i class="fa fa-plus"></i></a>--%>
                                            <%--</div>--%>
                                            <%--<div class="col-sm-1">--%>
                                                <%--<button class="btn btn-link pull-right" data-toggle="collapse"--%>
                                                        <%--data-target="#meta-${student.id}" aria-expanded="true">--%>
                                                    <%--<i class="fa fa-chevron-down"></i>--%>
                                                <%--</button>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<div id="add-modal-${student.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">--%>
                                        <%--<div role="document" class="modal-dialog">--%>
                                            <%--<div class="modal-content">--%>
                                                <%--<div class="modal-header">--%>
                                                    <%--<h4 id="exampleModalLabel" class="modal-title">Поставить оценку</h4>--%>
                                                    <%--<button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>--%>
                                                <%--</div>--%>
                                                <%--<div class="modal-body">--%>
                                                    <%--<form action="/lessons/${lesson.id}/groups/${group.id}/students/${student.id}" method="post">--%>
                                                        <%--<div class="form-group">--%>
                                                            <%--<input type="text" placeholder="Оценка" class="form-control" name="grade"--%>
                                                                <%--pattern="[0-9]*" title="Оценка должна содержать только цифры">--%>
                                                        <%--</div>--%>
                                                        <%--<div class="form-group">--%>
                                                            <%--<input type="text" placeholder="Заметка" class="form-control" name="note">--%>
                                                        <%--</div>--%>
                                                        <%--<div class="form-group">--%>
                                                            <%--<div class="col-sm-12">--%>
                                                                <%--<button type="submit" class="btn btn-primary pull-right">Сохранить</button>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                    <%--</form>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<div id="add-att-modal-${student.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">--%>
                                        <%--<div role="document" class="modal-dialog">--%>
                                            <%--<div class="modal-content">--%>
                                                <%--<div class="modal-header">--%>
                                                    <%--<h4 class="modal-title">Поставить посещаемость</h4>--%>
                                                    <%--<button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>--%>
                                                <%--</div>--%>
                                                <%--<div class="modal-body">--%>
                                                    <%--<form action="/lessons/${lesson.id}/groups/${group.id}/students/${student.id}/attendances" method="post">--%>
                                                        <%--<div class="form-group">--%>
                                                            <%--<div class="i-checks">--%>
                                                                <%--<input id="attendance-${student.id}" type="checkbox" name="attendance"--%>
                                                                       <%--class="checkbox-template" checked>--%>
                                                                <%--<label for="attendance-${student.id}">Присутствовал/а</label>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="form-group">--%>
                                                            <%--<input type="text" placeholder="Заметка" class="form-control" name="note">--%>
                                                        <%--</div>--%>
                                                        <%--<div class="form-group">--%>
                                                            <%--<div class="col-sm-12">--%>
                                                                <%--<button type="submit" class="btn btn-primary pull-right">Сохранить</button>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                    <%--</form>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<div id="meta-${student.id}" class="collapse" aria-labelledby="student-${student.id}" data-parent="#accordion">--%>
                                        <%--<div class="card-body">--%>
                                            <%--<div class="list-group">--%>
                                                <%--<div class="row">--%>
                                                    <%--<div class="col-sm-3 offset-sm-4">--%>
                                                        <%--<c:forEach items="${marks.get(student.id)}" var="mark">--%>
                                                            <%--<span class="badge badge-info badge-lg position-relative">--%>
                                                                <%--<div class="tools-right tools-small">--%>
                                                                    <%--<form action="/lessons/${lesson.id}/groups/${group.id}/grades/${mark.id}" method="post">--%>
                                                                        <%--<button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>--%>
                                                                    <%--</form>--%>
                                                                <%--</div>--%>
                                                                <%--${mark.markValue}--%>
                                                                <%--<c:if test="${mark.markNotes != ''}">--%>
                                                                    <%--(${mark.markNotes})--%>
                                                                <%--</c:if>--%>
                                                            <%--</span>--%>
                                                        <%--</c:forEach>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="col-sm-4">--%>
                                                        <%--<c:forEach items="${attendances.get(student.id)}" var="attendance">--%>
                                                            <%--<span class="badge badge-info badge-lg position-relative">--%>
                                                                <%--<div class="tools-right tools-small">--%>
                                                                    <%--<form action="/lessons/${lesson.id}/groups/${group.id}/grades/${attendance.id}" method="post">--%>
                                                                        <%--<button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>--%>
                                                                    <%--</form>--%>
                                                                <%--</div>--%>
                                                                <%--<c:choose>--%>
                                                                    <%--<c:when test="${attendance.attendanceValue eq 0}">--%>
                                                                        <%--Отсутствовал--%>
                                                                    <%--</c:when>--%>
                                                                    <%--<c:otherwise>--%>
                                                                        <%--Присутствовал--%>
                                                                    <%--</c:otherwise>--%>
                                                                <%--</c:choose>--%>
                                                                <%--<c:if test="${attendance.attendanceNote ne ''}">--%>
                                                                    <%--(${attendance.attendanceNote})--%>
                                                                <%--</c:if>--%>
                                                            <%--</span>--%>
                                                        <%--</c:forEach>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="col-sm-1"></div>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</c:forEach>--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--</div>--%>
            </section>
        </tags:inner>
    </div>
</div>

</body>
<jsp:include page="partials/footer.jsp"/>
</html>
