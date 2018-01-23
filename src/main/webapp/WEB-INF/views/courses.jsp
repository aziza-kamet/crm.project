<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>CRM | Курсы </title>
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
                    <div class="row">
                        <div class="col-sm-11">
                            <h2 class="no-margin-bottom">Курсы</h2>
                        </div>
                        <c:if test="${user.role.name.equals('admin')}">
                            <div class="col-sm-1">
                                <h2>
                                    <a href="#" class="pull-right" data-toggle="modal" data-target="#add-modal"><i class="fa fa-plus"></i></a>
                                </h2>
                            </div>
                        </c:if>
                    </div>
                </div>
            </header>
            <section class="no-padding-bottom">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <c:choose>
                                <c:when test="${user.role.name.equals('admin')}">
                                    <div class="card">
                                        <div class="card-body">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th>Название</th>
                                                <th>Описание</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${courses}" var="course">
                                                <tr>
                                                    <td>${course.name}</td>
                                                    <td>${course.description}</td>
                                                    <td>
                                                        <a href="/courses/${course.id}/lessons">Уроки</a>
                                                    </td>
                                                    <td>
                                                        <a href="#" data-toggle="modal" data-target="#edit-modal"
                                                           data-action="/courses/${course.id}/edit" data-name="${course.name}"
                                                           data-description="${course.description}">
                                                            <i class="fa fa-pencil"></i>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <form action="/courses/${course.id}" method="post">
                                                            <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${user.role.name.equals('teacher')}">
                                    <div class="accordion">
                                        <c:forEach items="${courses}" var="course">
                                            <div class="card collapse-card">
                                                <div class="card-header" id="course-${course.id}">
                                                    <h1 class="mb-0" data-toggle="collapse"
                                                        data-target="#lessons-${course.id}" aria-expanded="true" aria-controls="collapseOne">
                                                            ${course.name}
                                                        <button class="btn btn-link pull-right">
                                                            <i class="fa fa-chevron-down"></i>
                                                        </button>
                                                    </h1>
                                                </div>

                                                <div id="lessons-${course.id}" class="collapse" aria-labelledby="course-${course.id}" data-parent="#accordion">
                                                    <div class="card-body">
                                                        <div class="list-group">
                                                            <c:forEach items="${course.lessons}" var="lesson">
                                                                <a href="/courses/${course.id}/lessons/${lesson.id}" class="list-group-item list-group-item-action border-0">${lesson.title}</a>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="accordion">
                                        <div class="card collapse-card">
                                            <div class="card-header" id="header">
                                                <div class="row">
                                                    <div class="col-sm-4"><h3>Курс</h3></div>
                                                    <div class="col-sm-3"><h3>Оценка</h3></div>
                                                    <div class="col-sm-4"><h3>Посещаемость</h3></div>
                                                    <div class="col-sm-1"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <c:forEach items="${courses}" var="course">
                                            <div class="card collapse-card">
                                                <div class="card-header" id="course-${course.id}">
                                                    <div class="row">
                                                        <div class="col-sm-4">
                                                            ${course.name}
                                                        </div>
                                                        <div class="col-sm-3">
                                                            <fmt:formatNumber var="mark_avg"
                                                              value="${marks.get(course.id).get('avg')}"
                                                              maxFractionDigits="2"  />
                                                            ${mark_avg}
                                                        </div>
                                                        <div class="col-sm-4">
                                                            <c:if test="${attendances.get(course.id).get('avg') ne null}">
                                                                <fmt:formatNumber var="att_avg"
                                                                  value="${attendances.get(course.id).get('avg')}"
                                                                  maxFractionDigits="2"  />
                                                                ${att_avg}%
                                                            </c:if>
                                                        </div>
                                                        <div class="col-sm-1">
                                                            <button class="btn btn-link pull-right" data-toggle="collapse"
                                                                    data-target="#lessons-${course.id}" aria-expanded="true" aria-controls="collapseOne">
                                                                <i class="fa fa-chevron-down"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div id="lessons-${course.id}" class="collapse" aria-labelledby="course-${course.id}" data-parent="#accordion">
                                                    <div class="card-body">
                                                        <div class="list-group">
                                                            <c:forEach items="${course.lessons}" var="lesson">
                                                                <c:set var="mark" value="${marks.get(course.id).get('lessons').get(lesson.id)}"/>
                                                                <c:set var="attendance" value="${attendances.get(course.id).get('lessons').get(lesson.id)}"/>
                                                                <div class="row">
                                                                    <div class="col-sm-4">
                                                                        <a href="/courses/${course.id}/lessons/${lesson.id}" class="list-group-item list-group-item-action border-0">${lesson.title}</a>
                                                                    </div>
                                                                    <div class="col-sm-3">
                                                                        <c:if test="${mark ne null}">
                                                                            <fmt:formatNumber var="mark_rounded"
                                                                              value="${mark[1]}"
                                                                              maxFractionDigits="2"  />
                                                                            ${mark_rounded} (сумма: ${mark[0]})
                                                                        </c:if>
                                                                    </div>
                                                                    <div class="col-sm-4">
                                                                        <c:if test="${attendance ne null}">
                                                                            <fmt:formatNumber var="att_rounded"
                                                                                              value="${attendance[1]}"
                                                                                              maxFractionDigits="2"  />
                                                                            ${att_rounded}% (сумма: ${attendance[0]})
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </section>
        </tags:inner>
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
            <form action="/courses" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control" name="name">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea placeholder="Описание" class="form-control" name="description"></textarea>
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

<div id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Редактировать курсы</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control js-name" name="name">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea class="form-control js-description" name="description"></textarea>
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

<script>
    $('#edit-modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var action = button.attr('data-action');
        var name = button.attr('data-name');
        var description = button.attr('data-description');
        var modal = $(this);
        modal.find('form').attr("action", action);
        modal.find('form').attr("method", "post");
        modal.find('.js-name').val(name);
        modal.find('.js-description').val(description);
    })
</script>

</html>
