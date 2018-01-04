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
    <title>CRM | Расписание </title>
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
                    <h2 class="no-margin-bottom">Расписание</h2>
                </div>
            </header>
            <section class="no-padding-bottom">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <c:if test="${user.role.name.equals('admin')}">
                                    <div class="card-close">
                                        <div class="dropdown">
                                            <a href="#" data-toggle="modal" data-target="#add-modal"><i class="fa fa-plus"></i></a>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="card-header d-flex align-items-center">
                                    &nbsp;
                                </div>
                                <div class="card-body">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Время</th>
                                                <th>Понедельник</th>
                                                <th>Вторник</th>
                                                <th>Среда</th>
                                                <th>Четверг</th>
                                                <th>Пятница</th>
                                                <th>Суббота</th>
                                                <th>Воскресенье</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${hours}" var="hour">
                                            <tr>
                                                <td>${hour.value}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Monday")).course.name}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Tuesday")).course.name}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Wednesday")).course.name}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Thursday")).course.name}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Friday")).course.name}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Saturday")).course.name}</td>
                                                <td>${schedule.get(hour.key).get(days.get("Sunday")).course.name}</td>
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
<jsp:include page="partials/footer.jsp"/>

</html>