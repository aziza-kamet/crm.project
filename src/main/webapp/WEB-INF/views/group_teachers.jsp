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
    <title>CRM | Учителя группы </title>
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
                            <h2 class="no-margin-bottom">Учителя группы</h2>
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
                            <div class="card">
                                <div class="card-body">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>ФИО</th>
                                            <c:if test="${user.role.name.equals('admin')}">
                                                <th></th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${teachers}" var="teacher">
                                            <tr>
                                                <td>${teacher.name} ${teacher.surname}</td>
                                                <c:if test="${user.role.name.equals('admin')}">
                                                    <td>
                                                        <form action="/groups/${gid}/teachers/${teacher.id}" method="post">
                                                            <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                        </form>
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
        </tags:inner>
    </div>
</div>
</body>

<div id="add-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="exampleModalLabel" class="modal-title">Добавить учителя</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/groups/${gid}/teachers" method="post">
                <div class="modal-body">
                    <div class="form-teacher">
                        <label>Учителя</label>
                        <c:forEach items="${teachersOut}" var="teacher">
                            <div class="i-checks">
                                <input id="teacher-out-${teacher.id}" type="checkbox" name="teachers" value="${teacher.id}" class="checkbox-template">
                                <label for="teacher-out-${teacher.id}">${teacher.name} ${teacher.surname}</label>
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
<jsp:include page="partials/footer.jsp"/>

</html>
