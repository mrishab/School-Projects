<!--Home page contains the links to the games only.
Every logged in user is redirected to this page if he/ she attempts to open
a game login or register page
-->
<?php
session_start();
if(!isset($_SESSION["user"])){
  header("Location:index.php"); // if user is not logged in, redirect to the login page
}
  include 'assets/script/php/dbConnect.php';
  include 'assets/script/php/function.php';
  // EXPLANATION  OF BELOW CODE IS SIDE BY SIDE IN STEPS
  $user = $_SESSION["user"]; // step1: load the user object from session
  $dbPerson = getPersonFromDb($user); // step2: use that object (username, password) to get the details (name, age, gender, etc) about the user from the database
  $_SESSION["person"] = $dbPerson; //step 3: then assign that person object to session

  //*******NOTE***** user vs person object : user object contains username/password, person object contains name,age,gender (the personal details)
?>
<!--Reading Person from database in the above php-->
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="assets/style/css/nav.css">
  <link rel="stylesheet" href="assets/style/css/home.css">
  <title>Home</title>
</head>
<body>
  <nav>
    <ul>
      <li><a href="home.php" class = "selected">Home</a></li>
      <li><a href="puzzle15.php">Puzzle15</a></li>
      <li><a href="memory-game.php">Memory Tiles</a></li>
      <li><a href="logout.php">Logout</a></li>
      <li><a href="about.php">About</a></li>

    </ul>
  </nav>
  <header>
    <h1> RC Games</h1>
    <h3>Welcome, <?php echo $dbPerson["fname"] ?></h3>
  </header>
  <main>
    <div id="menu">
      <div id = "games-cont">
        <div class="game-cont">
          <h3>Puzzle 15</h3>
          <a href="puzzle15.php">
            <img class = "game" src="assets/images/menu/puzzle15.png" alt="Puzzle 15.png">
          </a>
        </div>
        <div class="game-cont">
          <h3>Memory Tiles</h3>
          <a href="memory-game.php">
            <img class = "game" src="assets/images/menu/memorytiles.png" alt="Memory Tiles.png">
          </a>
        </div>
      </div>
    </div>
  </main>
  <footer>
    <p>Copyright 2017</p>
    <p>RC Games</p>
    <p>Instructor: Shabnam Mirshokraie</p>
  </footer>
</body>
</html>
