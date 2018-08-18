<!--Registeration page for making a new account-->
<?php
  session_start();
  if(isset($_SESSION["user"])){ // checks if the user is signed in already
    header("Location:home.php"); // then redirects him to the homepage
  }
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="assets/style/css/register.css">
  <link rel="stylesheet" href="assets/style/css/nav.css">
  <link rel="stylesheet" href="assets/style/css/master.css">
  <link rel="stylesheet" href="assets/style/css/popup.css">
  <title>Register</title>
</head>
<body>
  <nav>
    <ul>
      <li><a href="index.php" >Login</a></li>
      <li><a href="register.php" class = "selected">Register</a></li>
      <li><a href="about.php">About</a></li>

    </ul>
  </nav>
  <main>
    <div class="logo">
      <img id = "logo" src="assets/images/logo.png" alt="logo">
    </div>
    <div id="register">
      <form id="register-form" method="post">
        <ul>
          <li>
            <label for="username">Username</label>
            <input type="text" name = "username" placeholder="Ex: mrishab">
          </li>
          <li>
            <label for="password">Password</label>
            <input type="password" name = "password" placeholder="32 Characters Maximum">
          </li>
          <li>
            <label for="re-password">Confirm Password</label>
            <input type="password" name = "repass" placeholder="Re-Enter the same password for confirmation">
          </li>
          <li>
            <label for="fname">Name</label>
            <input type="text" name = "fname" placeholder="First Name">
            <input type="text" name = "lname" placeholder="Last Name">
          </li>
          <li>
            <label for="dob">Date of Birth</label>
            <input type="Date" name = "dob" placeholder="mm-dd-yyyy">
          </li>
          <li>
            <label for="gender">Gender</label>
            <input class = "agreement" type="radio" name = "gender" value = "m"><label for="gender" checked = "checked">Male</label>
            <input class = "agreement" type="radio" name = "gender" value = "f"><label for="gender">Female</label>
            <input class = "agreement" type="radio" name = "gender" value = "u"><label for="gender">Unspecified</label>
          </li>
          <li>
            <p>Do you agree to the terms and services?</p>
            <input class = "agreement" type="radio" name = "agreement" value = "yes"><label for="agreement">Yes</label>
            <input class = "agreement" type="radio" name = "agreement" value = "no"><label for="agreement">No</label>
          </li>
          <li>
            <button type = "submit" id="register-button">Sign Up</button>
          </li>
        </ul>
      </form>
      <span class = "hide" id = "popup"></span>

    </div>
    <script type="text/javascript" src = "assets/script/javascript/functions.js"></script>
    <script type="text/javascript" src = "assets/script/javascript/register.js"></script>
  </main>
  <footer>
    <p>Copyright 2017</p>
    <p>RC Games</p>
    <p>Instructor: Shabnam Mirshokraie</p>
  </footer>
</body>
</html>
