<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aziza
  Date: 11.11.17
  Time: 7:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>CRM | Курсы группы </title>
    <jsp:include page="partials/header.jsp"/>
</head>
<body>
<div class="page home-page">
    <jsp:include page="partials/menu.jsp"/>
    <div class="page-content d-flex align-items-stretch">
        <jsp:include page="partials/side_menu.jsp"/>

        <div class="content-inner">
            <!-- Page Header-->
            <header class="page-header">
                <div class="container-fluid">
                    <h2 class="no-margin-bottom">Курсы группы</h2>
                </div>
            </header>
            <section class="no-padding-bottom">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-close">
                                    <div class="dropdown">
                                        <a href="#" data-toggle="modal" data-target="#add-modal"><i class="fa fa-plus"></i></a>
                                    </div>
                                </div>
                                <div class="card-header d-flex align-items-center">
                                    &nbsp;
                                </div>
                                <div class="card-body">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Название</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${courses}" var="course">
                                            <tr>
                                                <%-- TODO mustn't show id as numeration --%>
                                                <th scope="row">${course.id}</th>
                                                <td>${course.name}</td>
                                                <td>
                                                    <form action="/groups/${gid}/courses/${course.id}" method="post">
                                                        <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
</body>

<div id="add-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="exampleModalLabel" class="modal-title">Добавить курс</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
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
        </div>
    </div>
</div>

<div id="schedule-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="exampleModalLabel1" class="modal-title">Добавить расписание</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="" method="post">
                <div class="modal-body">
                    <div class="form-course">
                        <label>День</label>
                        <select name="dayOfWeek">
                            <option value="0">Понедельник</option>
                            <option value="1">Вторник</option>
                            <option value="2">Среда</option>
                            <option value="3">Четверг</option>
                            <option value="4">Пятница</option>
                            <option value="5">Суббота</option>
                            <option value="6">Воскресенье</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" data-dismiss="modal" class="btn btn-secondary">Закрыть</button>
                    <button class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="partials/footer.jsp"/>

</html>
