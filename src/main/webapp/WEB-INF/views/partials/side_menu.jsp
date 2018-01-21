<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Side Navbar -->
<nav class="side-navbar <c:if test="${cookie.menu_shrinked.value == 'true'}">
    shrinked
</c:if>">
    <!-- Sidebar Header-->
    <div class="sidebar-header d-flex align-items-center">
        <div class="avatar">
            <a href="/profile" class="__no-padding">
                <img src="<c:url value="/resources/images/user.png"/>" class="img-fluid rounded-circle">
            </a>
        </div>
        <div class="title">
            <a href="/profile">
                <h1 class="h4">${user.name} ${user.surname}</h1>
            </a>
            <p>${user.role.name}</p>
        </div>
    </div>
    <c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
    <c:choose>
        <c:when test="${user.role.name.equals('admin')}">
            <!-- Sidebar Navidation Menus-->
            <ul class="list-unstyled">
                <li <c:if test="${url.equals('/teachers')}">
                    class="active"
                </c:if>> <a href="/teachers">
                    <i class="fa fa-graduation-cap"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Учителя
                    </span>
                </a></li>
                <li <c:if test="${url.equals('/students')}">
                    class="active"
                </c:if>> <a href="/students">
                    <i class="fa fa-user"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Ученики
                    </span>
                </a></li>
                <li <c:if test="${fn:startsWith(url, '/courses')}">
                    class="active"
                </c:if>> <a href="/courses">
                    <i class="fa fa-book"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Курсы
                    </span>
                </a></li>
                <li <c:if test="${fn:startsWith(url, '/groups')}">
                    class="active"
                </c:if>> <a href="/groups">
                    <i class="fa fa-group"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Группы
                    </span>
                </a></li>
            </ul>
        </c:when>
        <c:when test="${user.role.name.equals('teacher') or user.role.name.equals('student')}">
            <ul class="list-unstyled">
                <li <c:if test="${fn:startsWith(url, '/my_courses')}">
                    class="active"
                </c:if>> <a href="/my_courses">
                    <i class="fa fa-book"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Мои курсы
                    </span>
                </a></li>
                <li <c:if test="${fn:startsWith(url, '/my_groups') or fn:startsWith(url, '/groups')}">
                    class="active"
                </c:if>> <a href="/my_groups">
                    <i class="fa fa-group"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Мои группы
                    </span>
                </a></li>
                <li <c:if test="${fn:startsWith(url, '/schedule')}">
                    class="active"
                </c:if>> <a href="/schedule">
                    <i class="fa fa-calendar"></i>
                    <span class="sidebar-li-text <c:if test="${cookie.menu_shrinked.value == 'true'}">__hidden</c:if>">
                        Расписание
                    </span>
                </a></li>
            </ul>
        </c:when>
    </c:choose>
</nav>
