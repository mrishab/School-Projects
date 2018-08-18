<?php
//Establishes a connection with database

include 'server-details.php';

$conn = new mysqli($servername, $username, $password, $database);
if($conn->error){
  die ("Error Connecting to the server: ".$conn->error);
}
?>
