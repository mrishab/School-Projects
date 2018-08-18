<?php
//Contains all the php functions used throughout the project.
//Sorry, they might be a bit mixed up.

//COPIED from Assignment 4 of this course
//this function gets the user json string via post, decodes into a php object and returns it.
function getUser(){
  $user = $_POST["user"];
  $user = json_decode($user);
  return $user;
}

//taken the user object sent by the user and uses it to load the username/password from the database.
function getUserFromDB($user){
  global $conn;
  $uname = $user->username;
  $query = "SELECT * FROM users WHERE username = '$uname'";
  $results = $conn->query($query);

  if($results = $conn->query($query)){
    if($results->num_rows == 0){ // if username doesnt match, it means user doesnt exists
      $results = false; // then it returns boolean false
    }
    elseif ($results->num_rows == 1) { // if it matches one, that means user exists and it returns that object
      $results = $results->fetch_assoc();
    }
  }
  else{
    die("Error in getUserFromDB function. ->".$conn->error);
    //in any other case the script dies.
  }

  return $results;
}

//function checks whether all the fields were filled or not.
function allFieldsValid($user){
  $totalFields = 0; // counter for total number of fields
  $fieldsValid = 0; // counter for fields that are filled
  $invalFields; // stores names of fields that are not filled
  foreach($user as $key=>$value){
    $totalFields++; // increment for every field
    if ($value != null && $value != "" && $value != " "){
      $fieldsValid++; // incrment for only valid fields
    }
    else{
      $invalFields = $key; // if not filled, store the field name
    }
  }
  if($fieldsValid == $totalFields){ // if all the fields are filled
    return true;
  }
  else {
    return $invalFields; // returns all the fields that cannot be empty but are left empty
  }
}

//---------------EXCLUSIVELY REGISTRATION functions------------------

//creates an entry for username/password in the database
function createUser($user){
  global $conn;
  $uname = $user->username;
  $upass = $user->password;
  $query = "INSERT INTO users (username, password) VALUES('$uname', '$upass')";
  if($conn->query($query) === TRUE){
    return TRUE;
  }
  else{
    return $conn->error;
  }
}

//creates any entry for personal details in the database.
function createPersonInDb($user){
  global $conn;
  $uname = $user->username;
  $fname = $user->fname;
  $lname = $user->lname;
  $gender = $user->gender;
  $dob = $user->dob;
  $regUser = getUserFromDB($user);
  //needs the user from db to associate person entry with foreign key of the user's primary key
  $user_id = $regUser["id"];
  $query = "INSERT INTO person (fname, lname, gender, dob, user_id) VALUES('$fname', '$lname', '$gender', '$dob', '$user_id')";
  if($conn->query($query) === TRUE){
    return TRUE;
  }
  else{
    //in case a user is created but it fails to create a person entry in the database
    // it deletes the user entry from the database.
    // otherwise there will be a combination for username/password but there wont be a
    // corresponding person entry.
    $rmUser = "DELETE FROM users WHERE username = '$uname'";
    $conn->query($rmUser);
    return $conn->error;
  }
}

//------------------REGISTER FUNCTION ENDS---------------------
//----------------EXCLUSIVELY LOGIN functions-------------------

//function for getting person object from the database
function getPersonFromDb($user){
  global $conn, $dbUser;
  $user_id = (getUserFromDB($user)["id"]);
  $query = "SELECT * FROM person WHERE user_id = '$user_id'";
  $result = $conn->query($query);
  $row = $result->fetch_assoc();
  return $row;
}
//-----------------LOGIN FUNCTION ENDS----------------------------
?>
