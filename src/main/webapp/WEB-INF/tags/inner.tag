<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" %>

<div class="content-inner <c:if test="${cookie.menu_shrinked.value == 'true'}">
    active
</c:if>">
    <jsp:doBody/>
</div>