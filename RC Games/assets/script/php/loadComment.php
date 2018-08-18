<?php
//Page for already stored comments from the database

    $query = "SELECT fname, lname, comment FROM comments inner join person WHERE comments.person_id = person.id and comments.game_id = '$game_id'";
    $results = $conn->query($query);

  if($results->num_rows > 0){
      while($row = $results->fetch_assoc()){
        echo "<li class = \"comment\"><div class=\"user\">".$row["fname"]." ".$row["lname"]."</div><p class = \"comment-para\">".$row["comment"]."</p></li>";
      }
    }
    //if there is no comment posted for that webpage, then message below is shown
    else{
      echo "Be the first one to post a comment";
    }
?>
