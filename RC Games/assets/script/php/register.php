<?php
//Page for signing up a new user account

  include 'function.php';
  include 'dbConnect.php';

  $user = getUser(); //gets the json user object when signup form is submitted, and returns decoded php object.
  //the above variable contains all the details filled up the user
  $dbUser = getUserFromDB($user);
  // the above function retrieves the same user from the database.
  //this is for the purpose of checking if the user already exists in the database
  //it only checks for the username, rest of the details can be same for unique users.

  if(!$dbUser){ // if dbUser is false, means username is unique, so it proceed with the process of signup
    $check = allFieldsValid($user);
    if($check === TRUE){ // checks if all the fields are filled.
      if($user->repass == $user->password){ // checks if user entered the same password both the times
        if($user->agreement == "yes"){ // checks if user agreed to the terms of service
          $response = createUser($user); // if it passes all the tests, it creates that user (username, password) in the database
          if ($response === TRUE){ // when the user is created, it creates any entry for person in the database
            $response = createPersonInDb($user);
            if($response === TRUE){
              echo "User registered successfully"; // if both the entries for user and person is made, this message is returned via ajax
            }
            else{
              echo "Error is creating user: ".$response;
            }
          }
          else{
            echo "Error is Registering User ID: ".$response;
          }
        }
        else{
          echo "Please agree to terms and conditions to Register";
        }
      }
      else{
        echo "Passwords do not match";
      }
    }
    else{
        echo "$check cannot be empty";
        //this returns list of all the fields that user did not enter.
    }
  }
  else{
    echo "User already exists";
    //the above else statement returns respective errors that may occur during the process
  }
?>
