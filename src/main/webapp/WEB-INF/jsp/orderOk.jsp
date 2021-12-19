<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Услуги</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<div class="page">

    <div class="header">
        <h1 class="header-title"> Салон красоты</h1>
    </div>

    <div class="nav">
        <a class="nav-link" href="/">Главная</a>
        <a class="nav-link"href="/beautysalon/service">Услуги</a>
        <a class="nav-link" href="#">Мастера</a>
        <a class="nav-link" href="login.html">Логин</a>
        <a class="nav-link" href="register.html">Регистрация</a>
    </div>
    <div>
        <h3>
            Заявка успешно создана для ${sessionScope.secureUser.login}
        </h3>
        <div>
            <table>
                <tr>
                    <td>Дата</td>
                    <td>${requestScope.ordering.orderDateTime.dayOfMonth}
                        .${requestScope.ordering.orderDateTime.month.value}
                        .${requestScope.ordering.orderDateTime.year}
                    </td>
                </tr>
                <tr>
                    <td>Время</td>
                    <td>${requestScope.ordering.orderDateTime.hour}
                        :${requestScope.ordering.orderDateTime.minute}
                    </td>
                </tr>
                <tr>
                    <td>Присвоенный ID</td>
                    <td>${requestScope.ordering.id}</td>
                </tr>
                <tr>
                    <td>Статус</td>
                    <td>${requestScope.ordering.status}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
