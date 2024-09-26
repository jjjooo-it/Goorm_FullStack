<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
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
        .wrapper {
            text-align: center;
        }
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 400px;
            margin-top: 10px;
            position: relative;
        }
        .form-header {
            font-size: 2em;
            color: #b3a1a1;
        }
        form input[type="text"],
        form input[type="password"],
        form input[type="email"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        form input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #b3a1a1;
            border: none;
            border-radius: 4px;
            color: white;
            cursor: pointer;
        }
        form input[type="submit"]:hover {
            background-color: #9f8b8b;
        }
        /* Remove the default outline on focus */
        form input:focus {
            outline: none;
        }
        /* Add margin to create space between button and link */
        .form-container form input[type="submit"] {
            margin-bottom: 20px; /* Adjust this value as needed */
        }
        /* Modal Styles */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .modal-content {
            background-color: #fff;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
            border-radius: 8px;
            position: relative;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        /* Style for the login link */
        .link a {
            color: #b3a1a1; /* Same color as the button */
            text-decoration: underline;
            font-size: 0.9em;
        }
        .link a:hover {
            color: #9f8b8b;
        }

        .link {
            text-align: center;
            margin-top: 10px;
            color: gray;
        }
        .link span {
            color: #b3a1a1;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <div class="form-header">Simple Todo</div>
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/register" method="post">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <input type="email" id="email" name="email" placeholder="Email" required>
            <input type="submit" value="회원가입하기">
        </form>
        <div class="link">
            <span>이미 계정이 있으신가요?</span> <a href="${pageContext.request.contextPath}/login">로그인하러 가기</a>
        </div>
    </div>
</div>

<!-- The Modal -->
<div id="errorModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" id="closeModal">&times;</span>
        <p id="errorMessage"></p>
    </div>
</div>

<script>
    window.onload = function() {
        var error = '<%= request.getAttribute("error") %>';
        if (error) { // Check if error is not null or empty
            var modal = document.getElementById("errorModal");
            var errorMessage = document.getElementById("errorMessage");
            var span = document.getElementById("closeModal");
            errorMessage.textContent = error;
            if(errorMessage.textContent!="null"){
                modal.style.display = "flex";
            }

            span.onclick = function() {
                modal.style.display = "none";
            }

            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        }
    };
</script>
</body>
</html>
