<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Side Navbar -->
<nav class="side-navbar <c:if test="${cookie.menu_shrinked.value == 'true'}">
    shrinked
</c:if>">
    <!-- Sidebar Header-->
    <div class="sidebar-header d-flex align-items-center">
        <div class="title">
            <h1>${user.login}</h1>
        </div>
    </div>
    <!-- Sidebar Navidation Menus-->
    <c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
    <ul class="list-unstyled">
        <li <c:if test="${url.equals('/superadmin/superadmins')}">
                class="active"
        </c:if>> <a href="/superadmin/superadmins">
            <i class="icon-home"></i>
            <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                Суперадмины
            </span>
        </a></li>
        <li <c:if test="${url.equals('/superadmin/admins')}">
            class="active"
        </c:if>> <a href="/superadmin/admins">
            <i class="icon-grid"></i>
            <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                Админы
            </span>
        </a></li>
        <li <c:if test="${url.equals('/superadmin/companies')}">
            class="active"
        </c:if>> <a href="/superadmin/companies">
            <i class="fa fa-university"></i>
            <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                Компании
            </span>
        </a></li>
    </ul>
</nav>
