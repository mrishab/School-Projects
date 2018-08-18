<!--This is a memory game designed specifically for this particular project. It is not copied/derived from any source-->
<?php
session_start();
if(!isset($_SESSION["user"])){
  header("Location:index.php"); // Checking for user logged in. If not, redirects to the login page
}
$game_id = 1;
  include 'assets/script/php/dbConnect.php'; // connects to the database
  include 'assets/script/php/function.php'; // contains functions.
  $user = $_SESSION["user"]; //loads the user object from session. This is for posting comments.
  $_SESSION["game_id"] = $game_id;
?>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Memory Tiles - Game</title>
    <link rel="stylesheet" href="assets/style/css/memory_game.css">
    <link rel="stylesheet" href="assets/style/css/nav.css">
    <link rel="stylesheet" href="assets/style/css/comments.css">
    <link rel="stylesheet" href="assets/style/css/master.css">
    <link rel="stylesheet" href="assets/style/css/home.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  </head>
  <body>
    <nav>
      <ul>
        <li><a href="home.php">Home</a></li>
        <li><a href="puzzle15.php">Puzzle15</a></li>
        <li><a href="memory-game.php" class = "selected">Memory Tiles</a></li>
        <li><a href="logout.php">Logout</a></li>
        <li><a href="about.php">About</a></li>

      </ul>
    </nav>
    <header>
      <h1>RC Games</h1>
    </header>
    <main>
      <div class="section">
        <!--MEMORY GAME WITH JAVASCRIPT-->
        <div id="game">
          <div id="game-frame"></div>
          <div id="mem-game-status">
            <p id="status">Moves: 0</p>
            <button id="reset">Reset</button>
            <button id="submit">Submit</button>
          </div>
          <script type="text/javascript" src = "assets/script/javascript/memory-game.js"></script>
        </div>
        <!--MEMORY GAME WITH JAVASCRIPT ENDS-->
        <!-- The above code in between the comments is taken from assignment 2 directly
        and contains the major portion of the game. Few bits are scattered on rest of the page too-->
      </div>
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
