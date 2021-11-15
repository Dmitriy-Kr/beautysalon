<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account page</title>
    <link rel="stylesheet" href="../../css/w3.css">
</head>
<body>
<div class="w3-container">
    <h2>Салон красоты</h2>
    <p>Информация о пользователе</p>
<table class="w3-table-all">
    <tr>
        <td>login</td>
        <td>${account.login}</td>
    </tr>
    <tr>
        <td>password</td>
        <td>${account.password}</td>
    </tr>
    <tr>
        <td>role</td>
        <td>${account.role.name}</td>
    </tr>
    <tr>
        <td>id</td>
        <td>${account.id}</td>
    </tr>
</table>
</div>
<a href="../../index.jsp">Главная страница</a>
</body>
</html>
