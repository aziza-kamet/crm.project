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
                                                <c:forEach items="${days}" var="day">
                                                    <td>
                                                        <c:set var="current_schedule" value="${schedule.get(hour.key).get(day.key)}"/>
                                                        ${current_schedule.course.name}
                                                        <c:if test="${!(current_schedule.course eq null)}">
                                                            <c:choose>
                                                                <c:when test="${user.role.name.equals('admin')}">
                                                                    <a href="#" data-toggle="modal" data-target="#edit-modal"
                                                                       data-action="/groups/${gid}/schedule/${current_schedule.id}/edit" data-notes="${current_schedule.notes}"
                                                                       data-day_id="${current_schedule.dayId}" data-hour_id="${current_schedule.hourId}"
                                                                       data-group_id="${current_schedule.group.id}" data-course_id="${current_schedule.course.id}"
                                                                       data-start_date="${current_schedule.startDateString}" data-end_date="${current_schedule.endDateString}">
                                                                        <i class="fa fa-edit"></i>
                                                                    </a>
                                                                    <form action="/groups/${gid}/schedule/${current_schedule.id}" method="post">
                                                                        <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                                    </form>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    (${current_schedule.group.name})
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                    </td>
                                                </c:forEach>
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

<div id="add-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="exampleModalLabel" class="modal-title">Добавить урок в расписание</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/groups/${gid}/schedule" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Курс</label>
                        <select name="course" class="form-control">
                            <c:forEach items="${courses}" var="course">
                                <option value="${course.id}">${course.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>День</label>
                        <select name="day" class="form-control">
                            <c:forEach items="${days}" var="day">
                                <option value="${day.key}">${day.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Время</label>
                        <select name="hour" class="form-control">
                            <c:forEach items="${hours}" var="hour">
                                <option value="${hour.key}">${hour.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>День начала</label>
                        <input type="date" name="startDay" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>День окончания</label>
                        <input type="date" name="endDay" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Заметки</label>
                        <input type="text" name="notes" class="form-control">
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

<div id="edit-modal" tabindex="-1" role="dialog" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Добавить урок в расписание</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Курс</label>
                        <select name="course" class="form-control js-cid">
                            <c:forEach items="${courses}" var="course">
                                <option value="${course.id}">${course.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>День</label>
                        <select name="day" class="form-control js-did">
                            <c:forEach items="${days}" var="day">
                                <option value="${day.key}">${day.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Время</label>
                        <select name="hour" class="form-control js-hid">
                            <c:forEach items="${hours}" var="hour">
                                <option value="${hour.key}">${hour.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>День начала</label>
                        <input type="date" name="startDay" class="form-control js-start_date">
                    </div>
                    <div class="form-group">
                        <label>День окончания</label>
                        <input type="date" name="endDay" class="form-control js-end_date">
                    </div>
                    <div class="form-group">
                        <label>Заметки</label>
                        <input type="text" name="notes" class="form-control js-notes">
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

</body>
<jsp:include page="partials/footer.jsp"/>

<script>
    $('#edit-modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var action = button.attr('data-action');
        var did = button.attr('data-day_id');
        var hid = button.attr('data-hour_id');
        var gid = button.attr('data-group_id');
        var cid = button.attr('data-course_id');
        var startDate = button.attr('data-start_date');
        var endDate = button.attr('data-end_date');
        var notes = button.attr('data-notes');
        var modal = $(this);

        modal.find('form').attr('action', action);
        modal.find('form').attr('method', 'post');
        modal.find('.js-start_date').val(startDate);
        modal.find('.js-end_date').val(endDate);
        modal.find('.js-notes').val(notes);
        modal.find('.js-cid').val(cid);
        modal.find('.js-did').val(did);
        modal.find('.js-hid').val(hid);
    })
</script>

</html>
