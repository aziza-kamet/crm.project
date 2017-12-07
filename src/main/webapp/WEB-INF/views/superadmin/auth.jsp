<%--
  Created by IntelliJ IDEA.
  User: aziza
  Date: 10.11.17
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CRM | Авторизация</title>
    <jsp:include page="../partials/header.jsp"/>
</head>
<body>
    <div class="page login-page">
        <div class="container d-flex align-items-center">
            <div class="form-holder has-shadow">
                <div class="row">
                    <!-- Logo & Information Panel-->
                    <div class="col-lg-6">
                        <div class="info d-flex align-items-center">
                            <div class="content">
                                <div class="logo">
                                    <h1>Добро пожаловать в CRM для Админа!</h1>
                                </div>
                                <p>Не самый лучший CRM, но я стараюсь...</p>
                            </div>
                        </div>
                    </div>
                    <!-- Form Panel    -->
                    <div class="col-lg-6 bg-white">
                        <div class="form d-flex align-items-center">
                            <div class="content">
                                <form id="login-form" method="post" action="/superadmin/auth">
                                    <div class="form-group">
                                        <input id="login-username" type="text" name="login" required="" class="input-material">
                                        <label for="login-username" class="label-material">Логин</label>
                                    </div>
                                    <div class="form-group">
                                        <input id="login-password" type="password" name="password" required="" class="input-material">
                                        <label for="login-password" class="label-material">Пароль</label>
                                    </div>
                                    <button class="btn btn-primary">Войти</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<jsp:include page="../partials/footer.jsp"/>
</html>
