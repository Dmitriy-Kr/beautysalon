<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/style.css">
    <title>Салон красоты</title>
</head>
<body>
<div class="page">

    <div class="header">
        <h1 class="header-title"> Салон красоты</h1>
        <h2 class="header-subtitle">Добро пожаловать</h2>
        <h2 class="header-subtitle">ADMINPAGE</h2>
    </div>

    <div class="nav">
        <a class="nav-link" href="/">Главная</a>
        <a class="nav-link" href="/beautysalon/service">Заказы</a>
        <a class="nav-link" href="#">Персонал</a>
        <a class="nav-link" href="login.html">Финансы</a>
        <c:if test="${sessionScope.secureUser == null}">
            <a>
                    GUEST : guest
            </a>
        </c:if>
        <c:if test="${sessionScope.secureUser != null}">
            <a>
                    ${sessionScope.secureUser.role} : ${sessionScope.secureUser.login}
            </a>
            <a class="nav-link" href="/beautysalon/logout">Выход</a>
        </c:if>
    </div>
</div>
</body>
</html>