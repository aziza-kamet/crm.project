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
    <title>CRM | ${user.login} </title>
    <jsp:include page="../partials/header.jsp"/>
</head>
<body>
<div class="page home-page">
    <jsp:include page="../partials/superadmin/menu.jsp"/>
    <div class="page-content d-flex align-items-stretch">
        <jsp:include page="../partials/superadmin/side_menu.jsp"/>

        <tags:inner>
            <!-- Page Header-->
            <header class="page-header">
                <div class="container-fluid">
                    <h2 class="no-margin-bottom">Профайл</h2>
                </div>
            </header>
            <section class="no-padding-bottom">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="card">
                                <div class="card-header d-flex align-items-center">
                                    <h3 class="h4">Редактировать логин</h3>
                                </div>
                                <div class="card-body">
                                    <form class="form-horizontal" action="/superadmin/login" method="post">
                                        <div class="form-group row">
                                            <div class="col-sm-12">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group-material">
                                                            <input id="login" type="text" name="login" required class="input-material" value="${user.login}">
                                                            <label for="login" class="label-material active">Логин</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="line"></div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 offset-sm-9">
                                                <button type="submit" class="btn btn-primary">Сохранить</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="card">
                                <div class="card-header d-flex align-items-center">
                                    <h3 class="h4">Обновить пароль</h3>
                                </div>
                                <div class="card-body">
                                    <form class="form-horizontal" action="/superadmin/password" method="post">
                                        <div class="form-group row">
                                            <div class="col-sm-12">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group-material">
                                                            <input id="password" type="password" name="password" required class="input-material">
                                                            <label for="password" class="label-material">Пароль</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12">
                                                        <div class="form-group-material">
                                                            <input id="new-password" type="password" name="new-password" required class="input-material">
                                                            <label for="new-password" class="label-material">Новый пароль</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12">
                                                        <div class="form-group-material">
                                                            <input id="conf-password" type="password" name="conf-password" required class="input-material">
                                                            <label for="conf-password" class="label-material">Подтвердить пароль</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="line"></div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 offset-sm-9">
                                                <button type="submit" class="btn btn-primary">Сохранить</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </tags:inner>
    </div>
</div>
</body>
<jsp:include page="../partials/footer.jsp"/>
</html>
