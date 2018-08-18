<!--This is the login page. If any user is not logged in, he/she is redirected to this page-->
<?php
  session_start();
  if(isset($_SESSION["user"])){ // if the user is logged in already == (session[user] is set)
    header("Location:home.php"); // he/she is redirected to the home page
  }
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="assets/style/css/login.css">
  <link rel="stylesheet" href="assets/style/css/nav.css">
  <link rel="stylesheet" href="assets/style/css/master.css">
  <link rel="stylesheet" href="assets/style/css/popup.css">
  <title>Login</title>
</head>
<body>
  <nav>
    <ul>
      <li><a href="index.php" class = "selected">Login</a></li>
      <li><a href="register.php">Register</a></li>
      <li><a href="about.php">About</a></li>

    </ul>
  </nav>
  <main>
    <div id="login">
      <form id="login-form" method="post">
        <ul>
          <li><label for="username">Username</label><input type="text" name = "username" placeholder = "Example: mrishab"></li>
          <li><label for="password">Password</label><input type="password" name = "password"></li>
          <li id = "login-sec"><button type="submit" id = "login-button" name="button">Login!</button></li>
        </ul>
        <span class = "hide" id = "popup">Some messgae</span>
      </form>
      <ul>
      </ul>
    </div>
  </main>
  <footer>
    <p>Copyright 2017</p>
    <p>RC Games</p>
    <p>Instructor: Shabnam Mirshokraie</p>
  </footer>
  <script type="text/javascript" src = "assets/script/javascript/functions.js"></script>
  <!--Contains the javascript functions for whole project-->
  <script type="text/javascript" src = "assets/script/javascript/login.js"></script>
  <!--Contains the implementation of functions to login the user with ajax and json-->
</body>
</html>
