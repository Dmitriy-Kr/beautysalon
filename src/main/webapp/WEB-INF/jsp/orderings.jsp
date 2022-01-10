<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Услуги</title>
    <link rel="stylesheet" href="../../css/w3.css">
</head>
<body>
<div>
    <h2>Салон красоты</h2>
    <p>Наши услуги</p>

    <table class="w3-table-all">
        <tr>
            <th>Название услуги</th>
            <th>Клиент</th>
            <th>Мастер</th>
            <th>Дата</th>
            <th>Время</th>
        </tr>

        <c:forEach var="ordering" items="${sessionScope.orderings}">
            <tr>
                <td>
                        ${ordering.service.name}
                </td>
                <td><p>${ordering.client.name}</p>
                    <p>${ordering.client.surname}</p>
                </td>
                <td>
                    <p>${ordering.employee.name}</p>
                    <p>${ordering.employee.surname}</p>
                </td>
                <td>
                        ${ordering.orderDateTime.dayOfMonth}
                    .${ordering.orderDateTime.month.value}
                    .${ordering.orderDateTime.year}
                </td>
                <td>
                        ${ordering.orderDateTime.hour}
                    :${ordering.orderDateTime.minute}
                </td>

            </tr>
        </c:forEach>
    </table>
</div>
<div>
    <p>
        <a href="/">Главная страница</a>
    </p>
</div>
</body>
</html>
