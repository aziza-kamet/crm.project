<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Side Navbar -->
<nav class="side-navbar">
    <!-- Sidebar Header-->
    <div class="sidebar-header d-flex align-items-center">
        <div class="title">
            <h1 class="h4">${user.login}</h1>
        </div>
    </div>
    <!-- Sidebar Navidation Menus--><span class="heading">Управление пользователями</span>
    <c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
    <ul class="list-unstyled">
        <li <c:if test="${url.equals('/superadmin/superadmins')}">
                class="active"
        </c:if>> <a href="/superadmin/superadmins"><i class="icon-home"></i>Суперадмины</a></li>
        <li <c:if test="${url.equals('/superadmin/admins')}">
            class="active"
        </c:if>> <a href="/superadmin/admins"> <i class="icon-grid"></i>Админы</a></li>
    </ul>
    <span class="heading">Управление</span>
    <ul class="list-unstyled">
        <li <c:if test="${url.equals('/superadmin/companies')}">
            class="active"
        </c:if>> <a href="/superadmin/companies"> <i class="fa fa-building"></i>Компании</a></li>
    </ul>
</nav>
