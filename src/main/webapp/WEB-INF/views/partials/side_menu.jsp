<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Side Navbar -->
<nav class="side-navbar">
    <!-- Sidebar Header-->
    <div class="sidebar-header d-flex align-items-center">
        <div class="title">
            <h1 class="h4">${user.name} ${user.surname}</h1>
        </div>
    </div>
    <c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
    <c:choose>
        <c:when test="${user.role.name.equals('admin')}">
            <!-- Sidebar Navidation Menus-->
            <span class="heading">Управление пользователями</span>
            <ul class="list-unstyled">
                <li <c:if test="${url.equals('/teachers')}">
                    class="active"
                </c:if>> <a href="/teachers"></i>Учителя</a></li>
                <li <c:if test="${url.equals('/students')}">
                    class="active"
                </c:if>> <a href="/students"></i>Ученики</a></li>
            </ul>
            <span class="heading">Управление</span>
            <ul class="list-unstyled">
                <li <c:if test="${fn:startsWith(url, '/courses')}">
                    class="active"
                </c:if>> <a href="/courses"></i>Курсы</a></li>
                <li <c:if test="${fn:startsWith(url, '/groups')}">
                    class="active"
                </c:if>> <a href="/groups"></i>Группы</a></li>
            </ul>
        </c:when>
        <c:when test="${user.role.name.equals('teacher') or user.role.name.equals('student')}">
            <ul class="list-unstyled">
                <li <c:if test="${fn:startsWith(url, '/my_courses')}">
                    class="active"
                </c:if>> <a href="/my_courses"></i>Мои курсы</a></li>
                <li <c:if test="${fn:startsWith(url, '/my_groups') or fn:startsWith(url, '/groups')}">
                    class="active"
                </c:if>> <a href="/my_groups"></i>Мои группы</a></li>
            </ul>
            <c:if test="${user.role.name.equals('student')}">
                <ul class="list-unstyled">
                    <li <c:if test="${fn:startsWith(url, '/my_grades')}">
                        class="active"
                    </c:if>> <a href="/my_grades"></i>Мои оценки</a></li>
                    <li <c:if test="${fn:startsWith(url, '/my_attendances')}">
                        class="active"
                    </c:if>> <a href="/my_attendances"></i>Моя посещаемость</a></li>
                </ul>
            </c:if>
        </c:when>
    </c:choose>
</nav>
