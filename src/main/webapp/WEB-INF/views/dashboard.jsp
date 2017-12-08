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
            <section class="no-padding-bottom">

                <div class="content-fluid">
                    <div class="row">
                        <!-- Page Header-->
                        <div class="col-lg-10 offset-1">
                            <div class="recent-activities card">
                                <div class="card-close">
                                </div>
                                <div class="card-header">
                                    <h3 class="h4">Новости</h3>
                                </div>
                                <div class="card-body no-padding">
                                    <c:forEach items="${news}" var="post">
                                        <div class="item">
                                            <div class="row">
                                                <div class="col-2 date-holder text-right">
                                                    <div class="icon"><i class="fa fa-clock-o"></i></div>
                                                    <div class="date"><span class="text-info">${post.postDate}</span></div>
                                                </div>
                                                <div class="col-10 content">
                                                    <h5>${post.title}</h5>
                                                    <p>${post.content}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
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
                <h4 id="exampleModalLabel" class="modal-title">Добавить урок</h4>
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
<jsp:include page="partials/footer.jsp"/>
</html>
