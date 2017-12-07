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
        <div class="content-fluid">
            <div class="row">
                <!-- Page Header-->
                <div class="col-lg-12">
                    <div class="recent-activities card">
                        <div class="card-close">
                            <div class="dropdown">
                                <button type="button" id="closeCard8" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-ellipsis-v"></i></button>
                                <div aria-labelledby="closeCard8" class="dropdown-menu dropdown-menu-right has-shadow" style="display: none; position: absolute; transform: translate3d(17px, 27px, 0px); top: 0px; left: 0px; will-change: transform;" x-placement="bottom-end"><a href="#" class="dropdown-item remove"> <i class="fa fa-times"></i>Close</a><a href="#" class="dropdown-item edit"> <i class="fa fa-gear"></i>Edit</a></div>
                            </div>
                        </div>
                        <div class="card-header">
                            <h3 class="h4">Recent Activities</h3>
                        </div>
                        <div class="card-body no-padding">
                            <div class="item">
                                <div class="row">
                                    <div class="col-2 date-holder text-right">
                                        <div class="icon"><i class="icon-clock"></i></div>
                                        <div class="date"> <span>6:00 am</span><span class="text-info">6 hours ago</span></div>
                                    </div>
                                    <div class="col-10 content">
                                        <h5>Meeting</h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud.</p>
                                    </div>
                                </div>
                            </div>


                            <div class="item">
                                <div class="row">
                                    <div class="col-2 date-holder text-right">
                                        <div class="icon"><i class="icon-clock"></i></div>
                                        <div class="date"> <span>6:00 am</span><span class="text-info">6 hours ago</span></div>
                                    </div>
                                    <div class="col-10 content">
                                        <h5>Meeting</h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud.</p>
                                    </div>
                                </div>
                            </div>
                            <div class="item">
                                <div class="row">
                                    <div class="col-2 date-holder text-right">
                                        <div class="icon"><i class="icon-clock"></i></div>
                                        <div class="date"> <span>6:00 am</span><span class="text-info">6 hours ago</span></div>
                                    </div>
                                    <div class="col-10 content">
                                        <h5>Meeting</h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
