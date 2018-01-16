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
    <title>CRM | Компании </title>
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
                    <h2 class="no-margin-bottom">Компании</h2>
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
                                            <th>Название</th>
                                            <th>Ключевое слово(?)</th>
                                            <th>Описание</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${companies}" var="company">
                                            <tr>
                                                    <%-- TODO mustn't show id as numeration --%>
                                                <th scope="row">${company.id}</th>
                                                <td>${company.name}</td>
                                                <td>${company.keyword}</td>
                                                <td>${company.description}</td>
                                                <td>
                                                    <form action="/superadmin/companies/${company.id}" method="post">
                                                        <button class="btn btn-link"><i class="fa fa-remove"></i></button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <a href="#" data-toggle="modal" data-target="#edit-modal"
                                                       data-action="/superadmin/companies/${company.id}/edit" data-name="${company.name}"
                                                       data-desc="${company.description}">
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
        </tags:inner>
    </div>
</div>
</body>

<div id="add-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade text-left">
    <div role="document" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 id="exampleModalLabel" class="modal-title">Добавить компанию</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/superadmin/companies" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control" name="name">
                    </div>
                    <div class="form-group">
                        <label>Ключевое слово</label>
                        <input type="text" placeholder="Например: bitlab" class="form-control" name="keyword">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea class="form-control" name="description"></textarea>
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
                <h4 class="modal-title">Редактировать компанию</h4>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
            </div>
            <form action="/superadmin/companies" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" placeholder="Название" class="form-control js-name" name="name">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea class="form-control js-desc" name="description"></textarea>
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
        var name = button.attr('data-name');
        var desc = button.attr('data-desc');
        var modal = $(this);
        modal.find('form').attr("action", action);
        modal.find('form').attr("method", "post");
        modal.find('.js-name').val(name);
        modal.find('.js-desc').html(desc);
    })
</script>

</html>
