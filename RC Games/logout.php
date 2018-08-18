<!--The logout page destroys all the session variables and redirects user to the login page-->
<?php
  session_start();
  session_unset();
  session_destroy();
  header("Location:index.php");
  ?>
