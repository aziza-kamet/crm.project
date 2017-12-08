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
    <title>CRM | Уроки </title>
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
                    <h2 class="no-margin-bottom">Новости</h2>
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
                                            <th>Название</th>
                                            <th>Содержание</th>
                                            <th>Дата добавления(?)</th>
                                            <c:if test="${user.role.name.equals('admin')}">
                                                <th></th>
                                                <th></th>
                                            </c:if>
                                            <c:if test="${user.role.name.equals('teacher')}">
                                                <th>Группы</th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${news}" var="post">
                                            <tr>
                                                    <%-- TODO mustn't show id as numeration --%>
                                                <td>${post.title}</td>
                                                <td>${post.content}</td>
                                                <td>${post.postDate}</td>
                                                <c:if test="${user.role.name.equals('admin')}">
                                                    <td>
                                                        <form action="/news/${post.id}" method="post">
                                                            <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <a href="#" data-toggle="modal" data-target="#edit-modal"
                                                           data-action="/news/${post.id}/edit" data-title="${post.title}"
                                                           data-content="${post.content}">
                                                            <i class="fa fa-edit"></i>
                                                        </a>
                                                    </td>
                                                </c:if>
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
                <h4 id="exampleModalLabel" class="modal-title">Добавить запись</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/news" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control" name="title">
                    </div>
                    <div class="form-group">
                        <label>Содержание</label>
                        <textarea class="form-control" name="content"></textarea>
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
                <h4 id="exampleModalLabel1" class="modal-title">Редактировать запись</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control js-title" name="title">
                    </div>
                    <div class="form-group">
                        <label>Содержание</label>
                        <textarea class="form-control js-content" name="content"></textarea>
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
        var title = button.attr('data-title');
        var content = button.attr('data-content');
        var modal = $(this);
        modal.find('form').attr("action", action);
        modal.find('form').attr("method", "post");
        modal.find('.js-title').val(title);
        modal.find('.js-content').val(content);
    })
</script>
</html>
