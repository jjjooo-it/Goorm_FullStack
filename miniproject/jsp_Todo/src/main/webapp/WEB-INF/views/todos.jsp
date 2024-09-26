<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

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
    <title>Todo List</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
            position: relative;
        }
        .wrapper {
            text-align: center;
            position: relative; /* Relative positioning for the logout button */
        }
        .todo-header {
            font-size: 2em;
            color: #b3a1a1;
            margin-bottom: 10px;
        }
        .todo-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 300px;
            margin: 0 auto;
            position: relative;
        }
        .logout {
            background: none;
            border: none;
            cursor: pointer;
            color: #b3a1a1;
            font-size: 1em;
            text-decoration: underline;
            margin-top: 20px; /* Space between todo container and logout button */
        }
        .logout:hover {
            color: #9f8b8b;
        }
        .todo-form input[type="text"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .todo-form button {
            width: 100%;
            padding: 10px;
            background-color: #b3a1a1;
            border: none;
            border-radius: 4px;
            color: white;
            cursor: pointer;
        }
        .todo-form button:hover {
            background-color: #9f8b8b;
        }
        .todo-list {
            list-style: none;
            padding: 0;
        }
        .todo-list li {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        .todo-list li.completed {
            text-decoration: line-through;
            color: #ccc;
        }
        .todo-list span {
            flex-grow: 1;
            margin-left: 10px;
        }
        .todo-list button {
            background: none;
            border: none;
            cursor: pointer;
            color: #b3a1a1;
            font-size: 1em;
        }
        .todo-list button:hover {
            color: #9f8b8b;
        }
        .filters {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
        }
        .filters button {
            background: none;
            border: none;
            cursor: pointer;
            color: #b3a1a1;
        }
        .filters button:hover {
            color: #9f8b8b;
        }
        form input:focus {
            outline: none;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <div class="todo-header">${username}'s todos</div>
    <div class="todo-container">
        <form action="${pageContext.request.contextPath}/todos" method="post" class="todo-form">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="username" value="${param.username}">
            <input type="text" id="task" name="task" placeholder="해야 할 일을 입력해주세요." required>
            <button type="submit">Add</button>
        </form>
        <ul class="todo-list">
            <c:forEach var="todo" items="${todos}">
                <li class="${todo.completed ? 'completed' : ''}">
                    <form action="${pageContext.request.contextPath}/todos" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="toggle">
                        <input type="hidden" name="id" value="${todo.id}">
                        <input type="hidden" name="username" value="${param.username}">
                        <button type="submit">${todo.completed ? "↩" : "✔"}</button>
                    </form>
                    <span>${todo.task}</span>
                    <div>
                        <form action="${pageContext.request.contextPath}/todos" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="id" value="${todo.id}">
                            <input type="hidden" name="username" value="${param.username}">
                            <button type="submit">✎</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/todos" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="username" value="${param.username}">
                            <input type="hidden" name="id" value="${todo.id}">
                            <button type="submit">✖</button>
                        </form>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <button class="logout" onclick="location.href='/logout';">로그아웃</button>
</div>
</body>
</html>
