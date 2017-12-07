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
    <title>CRM | Админы </title>
    <jsp:include page="../partials/header.jsp"/>
</head>
<body>
<div class="page home-page">
    <jsp:include page="../partials/superadmin/menu.jsp"/>
    <div class="page-content d-flex align-items-stretch">
        <jsp:include page="../partials/superadmin/side_menu.jsp"/>
        <div class="content-inner">
            <!-- Page Header-->
            <header class="page-header">
                <div class="container-fluid">
                    <h2 class="no-margin-bottom">Админы</h2>
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
                                            <th>Логин</th>
                                            <th>Компания</th>
                                            <th>Имя</th>
                                            <th>Фамилия</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${users}" var="user">
                                            <tr>
                                                    <%-- TODO mustn't show id as numeration --%>
                                                <th scope="row">${user.id}</th>
                                                <td>${user.login}</td>
                                                <td>${user.company.name}</td>
                                                <td>${user.name}</td>
                                                <td>${user.surname}</td>
                                                <td>
                                                    <form action="/superadmin/admins/${user.id}" method="post">
                                                        <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <a href="#" data-toggle="modal" data-target="#edit-modal"
                                                       data-action="/superadmin/admins/${user.id}/edit" data-login="${user.login}"
                                                       data-cid="${user.company.id}" data-name="${user.name}" data-surname="${user.surname}">
                                                        <i class="fa fa-edit"></i>
                                                    </a>
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
                <h4 id="exampleModalLabel" class="modal-title">Добавить админа</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/superadmin/admins" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Логин</label>
                        <input type="text" placeholder="Логин" class="form-control" name="login">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" placeholder="Пароль" class="form-control" name="password">
                    </div>
                    <div class="form-group">
                        <label>Подтвердить пароль</label>
                        <input type="password" placeholder="Подтвердить пароль" class="form-control" name="conf-password">
                    </div>
                    <div class="form-group">
                        <label>Компания</label>
                        <select class="form-control" name="company">
                            <c:forEach items="${companies}" var="company">
                                <option value="${company.id}">${company.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" placeholder="Имя" class="form-control" name="name">
                    </div>
                    <div class="form-group">
                        <label>Фамилия</label>
                        <input type="text" placeholder="Фамилия" class="form-control" name="surname">
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
                <h4 class="modal-title">Редактировать админа</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Логин</label>
                        <input type="text" placeholder="Логин" class="form-control js-login" name="login">
                    </div>
                    <div class="form-group">
                        <label>Компания</label>
                        <select class="form-control js-cid" name="company">
                            <c:forEach items="${companies}" var="company">
                                <option value="${company.id}">${company.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" placeholder="Имя" class="form-control js-name" name="name">
                    </div>
                    <div class="form-group">
                        <label>Фамилия</label>
                        <input type="text" placeholder="Фамилия" class="form-control js-surname" name="surname">
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
<jsp:include page="../partials/footer.jsp"/>

<script>
    $('#edit-modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var action = button.attr('data-action');
        var login = button.attr('data-login');
        var cid = button.attr('data-cid');
        var name = button.attr('data-name');
        var surname = button.attr('data-surname');
        var modal = $(this);
        modal.find('form').attr("action", action);
        modal.find('form').attr("method", "post");
        modal.find('.js-login').val(login);
        modal.find('.js-cid').val(cid);
        modal.find('.js-name').val(name);
        modal.find('.js-surname').val(surname);
    })
</script>

</html>
