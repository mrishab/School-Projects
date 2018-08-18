<?php
//Page used to login the user

  session_start();

  include 'function.php';
  include 'dbConnect.php';

  $user = getUser();
  //gets the username and password, entered on the login page via ajax
  $dbUser = getUserFromDB($user);
  //it matches the username/password combination in the database
  if(!$dbUser){ //if username is not found
    echo "No user found with username: ".$user->username; // returns this
  }
  else{
    $upass = $user->password;
    if($dbUser["password"] == $upass){
      $_SESSION["user"] = $user;
      echo "User Authenticated"; // when successfully authenticated
    }
    else{
      echo "Incorrect Password"; // if username is found but password do not match.
    }
  }
?>
