<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Салон красоты</title>
</head>
<body>
<div class="page">

    <div class="header">
        <h1 class="header-title"> Салон красоты</h1>
        <h2 class="header-subtitle">Добро пожаловать</h2>
    </div>

    <div class="nav">
        <a class="nav-link" href="/">Главная</a>
        <a class="nav-link" href="/beautysalon/service">Услуги</a>
        <a class="nav-link" href="#">Мастера</a>
        <a class="nav-link" href="login.html">Логин</a>
        <a class="nav-link" href="register.html">Регистрация</a>
        <c:if test="${sessionScope.secureUser != null}">
            <a>
                    ${sessionScope.secureUser.role} : ${sessionScope.secureUser.login}
            </a>
        </c:if>
    </div>
</div>
</body>
</html>