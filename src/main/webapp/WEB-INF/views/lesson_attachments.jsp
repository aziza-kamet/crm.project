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
                    <h2 class="no-margin-bottom">Уроки</h2>
                </div>
            </header>
            <section class="no-padding-bottom">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <c:if test="${user.role.name.equals('teacher')}">
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
                                            <th></th>
                                            <c:if test="${user.role.name.equals('teacher')}">
                                                <th></th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${attachments}" var="attachment">
                                            <tr>
                                                    <%-- TODO mustn't show id as numeration --%>
                                                <td>${attachment.name}</td>
                                                <td>
                                                    <a href="/attachments/${attachment.id}/download" target="_blank">
                                                        <i class="fa fa-download"></i>
                                                    </a>
                                                </td>
                                                <c:if test="${user.role.name.equals('teacher')}">
                                                    <td>
                                                        <form action="/attachments/${attachment.id}" method="post">
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
        </div>
    </div>
</div>
</body>

<div id="add-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="exampleModalLabel" class="modal-title">Прикрепить файл к уроку</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/lessons/${lid}/attachments" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control" name="name">
                    </div>
                    <div class="form-group">
                        <label>Файл</label>
                        <input type="file" class="form-control" name="file">
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
