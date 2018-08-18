<!--Game taken from Assignment 2 of COURSE CIS-245 AB2 by Rishab Manocha (300139589)-->
<?php
session_start();
if(!isset($_SESSION["user"])){ //checks for user logged in.
  header("Location:index.php"); // if not logged in then redirects to login page
}
$game_id = 2; // game id assigned for purpose of loading comments from database

  include 'assets/script/php/dbConnect.php'; // establish connection with database
  include 'assets/script/php/function.php'; // no function is called in this file. only defined
  $user = $_SESSION["user"]; // loads the user object from the session to the user object on the page
  $_SESSION["game_id"] = $game_id; // sets the game id to use for retrieving comments from database
?>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Puzzle 15 - Game</title>
    <link rel="stylesheet" href="assets/style/css/nav.css">
    <link rel="stylesheet" href="assets/style/css/puzzle15.css">

    <link rel="stylesheet" href="assets/style/css/comments.css">
    <link rel="stylesheet" href="assets/style/css/home.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  </head>
  <body>
    <nav>
      <ul>
        <li><a href="home.php">Home</a></li>
        <li><a href="puzzle15.php" class = "selected">Puzzle15</a></li>
        <li><a href="memory-game.php">Memory Tiles</a></li>
        <li><a href="logout.php">Logout</a></li>
        <li><a href="about.php">About</a></li>

      </ul>
    </nav>
    <header>
      <h1> RC Games</h1>
    </header>
    <main>
      <div class="section">
        <!--Puzzle 15 WITH JAVASCRIPT-->
        <div id="frame"></div>
        <div id="status">
          <p id ="count">Moves: 0</p>
          <button id = "reset">Reset</button>
          <button id = "submit">Submit</button>
          <small>View the console for solution</small>
        <!--Puzzle 15 WITH JAVASCRIPT ENDS-->
        <!-- The above code in between the comments is taken from assignment 2 directly
        and contains the major portion of the game. Few bits are scattered on rest of the page too-->
        </div>
      </div>
      <script type="text/javascript" src = "assets/script/javascript/puzzle15.js"></script>
      <hr>
      <div class="section">
        <div id = "comments">
          <ul id = "comment-list">
            <?php include 'assets/script/php/loadComment.php';?>
            <!--loads the comments from database and post them on the page-->
            <!--This list item contains the input box for posting the comments.-->
            <li class = "comment" id = "input-comment-list">
              <input id = "input-comment" type="text" name="comment" placeholder="type here to post a comment">
              <button type="button" id = "comment-button" name="comment-button">Post</button>
            </li>
            <!--The input box for posting comments ends here-->
          </ul>
    </main>
    <script type="text/javascript" src = "assets/script/javascript/postComment.js"></script>
    <!--This script uploads the comments posted to the database as well as displays the one posted instantly on the page too-->
    <footer>
      <p>Copyright 2017</p>
      <p>RC Games</p>
      <p>Instructor: Shabnam Mirshokraie</p>
    </footer>
  </body>
</html>
