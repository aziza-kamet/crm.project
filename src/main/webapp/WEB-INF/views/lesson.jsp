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
    <title>CRM | Уроки </title>
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
                    <h2 class="no-margin-bottom">Курс: ${lesson.course.name}</h2>
                </div>
            </header>
            <section class="no-padding-bottom">
                <div class="container-fluid">
                    <div class="row">
                        <div class="
                            <c:choose>
                                <c:when test="${user.role.name.equals('teacher')}">
                                    col-lg-9
                                </c:when>
                                <c:otherwise>
                                    col-lg-12
                                </c:otherwise>
                            </c:choose>
                        ">
                            <div class="card">
                                <div class="card-body">
                                    <h2>${lesson.title}</h2>
                                    <p>${lesson.content}</p>

                                    <div class="row no-padding no-margin-bottom">
                                        <c:forEach items="${attachments}" var="attachment">
                                            <div class="col-sm-2">
                                                <a href="/lessons/${lesson.id}/attachments/${attachment.id}" target="_blank"
                                                   class="badge badge-info badge-lg position-relative">
                                                    <c:if test="${user.role.name.equals('teacher')}">
                                                        <div class="tools-right tools-small">
                                                            <form action="/attachments/${attachment.id}" method="post" class="no-margin">
                                                                <button class="btn btn-link no-padding"><i class="fa fa-remove"></i></button>
                                                            </form>
                                                        </div>
                                                    </c:if>
                                                    <i class="fa fa-file"></i>
                                                    ${attachment.name}
                                                </a>
                                            </div>
                                        </c:forEach>
                                        <c:if test="${user.role.name.equals('teacher')}">
                                            <div class="col-sm-2">
                                                <a href="#" class="badge badge-info badge-lg position-relative" data-toggle="modal" data-target="#add-modal">
                                                    <i class="fa fa-plus"></i>
                                                </a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${user.role.name.equals('teacher')}">
                            <div class="col-lg-3">
                                <div class="card">
                                    <div class="card-body">
                                        <c:forEach items="${groups}" var="group">
                                            <a href="/lessons/${lesson.id}/groups/${group.id}/grades">
                                                    ${group.name} <br>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
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
                <h4 id="exampleModalLabel" class="modal-title">Прикрепить файл к уроку</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/lessons/${lesson.id}/attachments" method="post" enctype="multipart/form-data">
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
