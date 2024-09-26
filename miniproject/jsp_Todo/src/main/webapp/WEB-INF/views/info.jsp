<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
        }
        .info-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 300px;
        }
        .info-header {
            font-size: 2em;
            color: #b3a1a1;
            text-align: center;
        }
        .info-item {
            margin: 10px 0;
            font-size: 1.2em;
        }
        .logout-form button {
            width: 100%;
            padding: 10px;
            background-color: #b3a1a1;
            border: none;
            border-radius: 4px;
            color: white;
            cursor: pointer;
        }
        .logout-form button:hover {
            background-color: #9f8b8b;
        }
        form input:focus {
            outline: none;
        }
    </style>
</head>
<body>
<div class="info-container">
    <div class="info-header">User Information</div>
    <div class="info-item">Username: ${user.username}</div>
    <div class="info-item">Email: ${user.email}</div>
    <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
        <button type="submit">Logout</button>
    </form>
</div>
</body>
</html>
