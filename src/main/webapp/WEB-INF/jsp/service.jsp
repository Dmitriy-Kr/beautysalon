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
            <th>Цена</th>
            <th><a title="Сортировать" href="/beautysalon/service?sort=surname">Мастер</a></th>
            <th><a title="Сортировать" href="/beautysalon/service?sort=rating">Рейтинг</a></th>
        </tr>

        <c:if test="${!sessionScope.isFilteredServiceList}">
            <c:forEach var="service" items="${sessionScope.serviceList}">
                <tr>
                    <td>
                        <a title="Фильтровать" href="/beautysalon/service?filter=service&name=${service.serviceName}">${service.serviceName}</a>
                    </td>
                    <td>${service.price}</td>
                    <td>
                        <a title="Фильтровать" href="/beautysalon/service?filter=employee&name=${service.employeeName}&surname=${service.employeeSurname}">
                            <p>${service.employeeName}</p>
                            <p>${service.employeeSurname}</p>
                        </a>
                    </td>
                    <td>${service.employeeRating}</td>
                    <c:if test="${sessionScope.secureUser.role == 'CLIENT'}">
                        <td>
<%--                            <a title="Заказать услугу" href="/beautysalon/order?service_id=${service.serviceId}&employee_id=${service.employeeId}">--%>
                            <a title="Заказать услугу" href="/beautysalon/order?note_number=${sessionScope.serviceList.indexOf(service)}">
                            Заказать услугу
                            </a>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${sessionScope.isFilteredServiceList}">
            <c:forEach var="service" items="${sessionScope.serviceListFiltered}">
                <tr>
                    <td>
                        ${service.serviceName}
                    </td>
                    <td>${service.price}</td>
                    <td>
                            <p>${service.employeeName}</p>
                            <p>${service.employeeSurname}</p>
                    </td>
                    <td>${service.employeeRating}</td>
                    <c:if test="${sessionScope.secureUser.role == 'CLIENT'}">
                        <td>
                            <a title="Заказать услугу" href="/beautysalon/order?note_number=${sessionScope.serviceList.indexOf(service)}">
                                Заказать услугу
                            </a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </c:if>

    </table>
</div>
<div>
    <p>
        <a href="/">Главная страница</a>
    </p>
</div>
</body>
</html>
