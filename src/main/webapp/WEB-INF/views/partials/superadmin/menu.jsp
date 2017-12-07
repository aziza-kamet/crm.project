<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Main Navbar-->
<header class="header">
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-holder d-flex align-items-center justify-content-between">
                <!-- Navbar Header-->
                <div class="navbar-header">
                    <!-- Navbar Brand --><a href="/superadmin" class="navbar-brand">
                    <div class="brand-text brand-big"><span>CRM </span><strong>Админ</strong></div>
                    <div class="brand-text brand-small">CRM</div></a>
                    <!-- Toggle Button--><a id="toggle-btn" href="#" class="menu-btn active"><span></span><span></span><span></span></a>
                </div>
                <!-- Navbar Menu -->
                <ul class="nav-menu list-unstyled d-flex flex-md-row align-items-md-center">
                    <!-- Search-->
                    <li class="nav-item d-flex align-items-center"><a id="search" href="#"><i class="icon-search"></i></a></li>
                    <!-- Logout    -->
                    <li class="nav-item">
                        <form action="/logout" method="post">
                            <button class="nav-link logout"> Выйти <i class="fa fa-sign-out"></i></button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>