<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ordering page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/style.css">
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
    </div>
        <c:if test="${sessionScope.serviceList.get(param.note_number) != null}">
            <c:set var="service" value="${sessionScope.serviceList.get(param.note_number)}"/>
            <form action="/beautysalon/order" method="post">
                <input type="hidden" name="serviceId" value="${service.serviceId}">
                <input type="hidden" name="employeeId" value="${service.employeeId}">
                <div>
                    <table>
                        <tr>
                            <th>Название услуги</th>
                            <th>Цена</th>
                            <th>Мастер</th>
                            <th>Рейтинг</th>
                            <th>Дата время</th>
                        </tr>
                        <tr>
                            <td>
                                    ${service.serviceName}
                            </td>
                            <td>
                                    ${service.price}
                            </td>
                            <td>
                                <p>${service.employeeName}</p>
                                <p>${service.employeeSurname}</p>
                            </td>
                            <td>
                                    ${service.employeeRating}
                            </td>
                            <td>
                                <input type="datetime-local" name="order_date">
                            </td>
                        </tr>
                    </table>
                </div>
                <button class="btn" type="submit">Оформить</button>
            </form>
        </c:if>
    </div>
</body>
</html>
