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
    <title>CRM | Посещаемость</title>
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
                        Посещаемость группы <b>${group.name}</b> ${lesson.title}
                        <a href="/lessons/${lesson.id}/groups/${group.id}/grades" class="pull-right">Оценки</a>
                    </h2>
                </div>
            </header>
            <br><br><br>
            <section class="tables">
                <div class="container-fluid">
                    <div class="row">
                        <c:forEach items="${students}" var="student">
                            <div class="col-lg-6">
                                <div class="card">
                                    <div class="card-close">
                                        <div class="dropdown">
                                            <a href="#" data-toggle="modal" data-target="#add-modal-${student.id}"><i class="fa fa-plus"></i></a>
                                        </div>
                                    </div>
                                    <div class="card-header d-flex align-items-center">
                                        <h3 class="h4">${student.name} ${student.surname}</h3>
                                    </div>
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th>Оценка</th>
                                                <th>Заметка</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${attendances.get(student.id)}" var="attendance">
                                                <tr>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${attendance.attendanceValue == 0}">
                                                                Отсутствовал
                                                            </c:when>
                                                            <c:otherwise>
                                                                Присутствовал
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${attendance.attendanceNote}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <div id="add-modal-${student.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
                                <div role="document" class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 id="exampleModalLabel" class="modal-title">Добавить урок</h4>
                                            <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="/lessons/${lesson.id}/groups/${group.id}/students/${student.id}/attendances" method="post">
                                                <div class="form-group">
                                                    <div class="i-checks">
                                                        <input id="attendance-${student.id}" type="checkbox" name="attendance"
                                                               class="checkbox-template" checked>
                                                        <label for="attendance-${student.id}">Присутствовал/а</label>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <input type="text" placeholder="Заметка" class="form-control" name="note">
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <button type="submit" class="btn btn-primary pull-right">Сохранить</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                </div>
            </section>
        </tags:inner>
    </div>
</div>

</body>
<jsp:include page="partials/footer.jsp"/>
</html>
