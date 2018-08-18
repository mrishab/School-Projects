<?php
//Script for loading the posted comments in the database

session_start();
include 'dbConnect.php';
  $person = $_SESSION["person"];
  $person_id = $person["id"];
  $game_id = $_SESSION["game_id"];
  $comment = $_POST["comment"];
  $query = "INSERT INTO comments (person_id, comment, game_id) VALUES ('$person_id','$comment','$game_id')";
  $conn->query($query);

  echo "<div class=\"user\">".$person["fname"]." ".$person["lname"]."</div><p class = \"comment-para\">".$comment."</p>";
//after making an entry of the comments in the database, it returns a list item with that comments.
//this ensures that comment is not displayed until it is stored in the database successfully.
?>
