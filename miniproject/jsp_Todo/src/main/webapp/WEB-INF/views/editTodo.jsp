<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Todo</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
            flex-direction: column;
        }
        .form-header {
            font-size: 2em;
            color: #b3a1a1;
            text-align: center;
            margin-bottom: 10px;
        }
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 300px;
        }
        form input[type="text"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        form input[type="checkbox"] {
            margin: 10px 0;
        }
        form button {
            width: 100%;
            padding: 10px;
            background-color: #b3a1a1;
            border: none;
            border-radius: 4px;
            color: white;
            cursor: pointer;
        }
        form button:hover {
            background-color: #9f8b8b;
        }
        form input:focus {
            outline: none;
        }
    </style>
</head>
<body>
<div class="form-header">Edit Todo</div>
<div class="form-container">
    <form action="${pageContext.request.contextPath}/todos" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${todo.id}">
        <input type="hidden" name="username" value="${todo.username}">
        <input type="text" id="task" name="task" value="${todo.task}" required>
        <button type="submit">Update</button>
    </form>
</div>
</body>
</html>
