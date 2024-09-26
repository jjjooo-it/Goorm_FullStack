<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
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
        .form-header {
            font-size: 2em;
            color: #b3a1a1;
            margin-bottom: 10px;
        }
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 300px;
            margin: 0 auto;
        }
        form input[type="text"],
        form input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
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
        .error {
            color: red;
            text-align: center;
        }
        form input:focus {
            outline: none;
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
    </style>
</head>
<body>
<div class="wrapper">
    <div class="form-header">Login</div>
    <div class="form-container">
        <form action="login" method="post">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
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
