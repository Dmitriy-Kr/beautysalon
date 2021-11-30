<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ordering page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
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
        <c:if test="${sessionScope.secureUser == null}">
            <a>
                GUEST : guest
            </a>
        </c:if>
        <c:if test="${sessionScope.secureUser != null}">
            <a>
                    ${sessionScope.secureUser.role} : ${sessionScope.secureUser.login}
            </a>
            <a class="nav-link" href="#">Выход</a>
        </c:if>
    </div>
    <div class="auth">
        <h1 class="auth-title">Оформление заказа</h1>
        <form action="/beautysalon/order" method="post">

            <div class="form-group">
                <select name="service">
                    <c:forEach var="service" items="${sessionScope.serviceList}">
                        <option value="${service.serviceId}">${service.serviceName}</option>
                    </c:forEach>
                </select>
                <%--                <input class="input" type="text" name="login" placeholder="Введите login">--%>
            </div>

            <div class="form-group">
                <select name="employee">
                <c:forEach var="service" items="${sessionScope.serviceList}">
                    <option value="${service.employeeId}">${service.employeeName}${service.employeeSurname}</option>
                </c:forEach>
                </select>
                <%--                <input class="input" type="password" name="password" placeholder="Введите пароль">--%>
            </div>

            <button class="btn" type="submit">Оформить</button>

        </form>
    </div>
</div>

</body>
</html>
