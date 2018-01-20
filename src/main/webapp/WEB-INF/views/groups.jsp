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
<html lang="ru">
<head>
    <title>CRM | Группы </title>
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
                            <h2 class="no-margin-bottom">Группы</h2>
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
                            <c:forEach items="${groups}" var="group">
                                <div class="project position-relative">
                                    <c:if test="${user.role.name.equals('admin')}">
                                        <div class="tools-right">
                                            <form action="/groups/${group.id}" method="post" class="no-margin">
                                                <button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>
                                            </form>
                                            <a href="#" data-toggle="modal" data-target="#edit-modal"
                                               data-action="/groups/${group.id}/edit" data-name="${group.name}">
                                                <i class="fa fa-pencil"></i>
                                            </a>
                                        </div>
                                    </c:if>
                                    <div class="row bg-white has-shadow">
                                        <div class="col-sm-2 d-flex align-items-center">
                                            <h1>${group.name}</h1>
                                        </div>
                                        <div class="col-sm-7">
                                            <%--<div class="d-flex justify-content-start align-content-center">--%>
                                            <div class="row no-padding no-margin-bottom horizontal-list">
                                                <c:forEach items="${group.courses}" var="course">
                                                    <div class="col-sm-2">
                                                        <span class="badge badge-info badge-lg position-relative">
                                                            <c:if test="${user.role.name.equals('admin')}">
                                                                <div class="tools-right tools-small">
                                                                    <form action="/groups/${group.id}/courses/${course.id}" method="post" class="no-margin">
                                                                        <button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>
                                                                    </form>
                                                                </div>
                                                            </c:if>
                                                            ${course.name}
                                                        </span>
                                                    </div>
                                                </c:forEach>
                                                <c:if test="${user.role.name.equals('admin')}">
                                                    <div class="col-sm-2">
                                                        <a href="#" class="badge badge-info badge-lg position-relative" data-toggle="modal" data-target="#add-course-modal"
                                                           data-action="/groups/${group.id}/courses" data-gid="${group.id}">
                                                            <i class="fa fa-plus"></i>
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 d-flex align-items-center justify-content-end tools-horizontal">
                                            <c:if test="${user.role.name.equals('admin')}">
                                                <a href="/groups/${group.id}/schedule"><h1><i class="fa fa-calendar"></i></h1></a>
                                            </c:if>
                                            <a href="/groups/${group.id}/students"><h1><i class="fa fa-user"></i></h1></a>
                                            <a href="/groups/${group.id}/teachers"><h1><i class="fa fa-graduation-cap"></i></h1></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
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
                <h4 id="exampleModalLabel" class="modal-title">Добавить группу</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/groups" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control" name="name">
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
                <h4 class="modal-title">Редактировать группу</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control js-name" name="name">
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

<div id="add-course-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Добавить курс</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <div id="courses-out"></div>
        </div>
    </div>
</div>

<jsp:include page="partials/footer.jsp"/>

<script>
    $('#edit-modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var action = button.attr('data-action');
        var name = button.attr('data-name');
        var modal = $(this);
        modal.find('form').attr("action", action);
        modal.find('form').attr("method", "post");
        modal.find('.js-name').val(name);
    });
    $('#add-course-modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var action = button.attr('data-action');
        var gid = button.attr('data-gid');
        var modal = $(this);

        $.ajax({
            type: 'get',
            url: '/groups/' + gid + '/courses/out',
            success: function (data) {
                $('#courses-out').html(data);
            }
        });
    });
</script>

</html>
