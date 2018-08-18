<?php
  session_start();
?>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Memory Tiles - Game</title>
    <link rel="stylesheet" href="assets/style/css/nav.css">
    <link rel="stylesheet" href="assets/style/css/master.css">
    <link rel="stylesheet" href="assets/style/css/about.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  </head>
  <body>
    <nav>
      <ul>
        <?php
        if(isset($_SESSION["user"])){
          echo "
          <li><a href=\"home.php\">Home</a></li>
          <li><a href=\"puzzle15.php\">Puzzle15</a></li>
          <li><a href=\"memory-game.php\" >Memory Tiles</a></li>
          <li><a href=\"logout.php\">Logout</a></li>
          ";
        }
        else{
          echo "
          <li><a href=\"index.php\">Login</a></li>
          <li><a href=\"register.php\">Register</a></li>
          ";
        }
        ?>
        <li><a href="about.php" class = "selected">About</a></li>
      </ul>
    </nav>
    <header>
      <h1>RC Games</h1>
    </header>
    <main>
      <h2>What is RC Games?</h2>
      <p>RC Games is a social gaming website for people of all age.</p>
      <p>On this website, kids, teenagers and adults can play simple games like memory tiles
       and Puzzle 15 (we currently support only those two). These games let you relax
     and have some fun time off from your tiring schedule</p>
     <p>
       Puzzle 15 game is set of tiles which have been scattered randomly. The tiles can be
       arranged back in the numeric sequence which sliding them. You can only slide tile to
       the adjacent empty area.
     </p>
     <p>
       Memory Tiles game contains pairs various programming related images underneath
       every tile. To succeed, one needs to match all the tiles. You can only flip two tiles
       at a time. The tiles that match will automatically stay open. In case, you flip tiles that
       do not match, they will stay flipped for a couple of seconds and unflip after that. Your
       task is to memorise their location and flip them when you find their corresponding partner.
     </p>
     <div class="contact">
       <h2>Contact Us</h2>

       <p>RC Games is built by:</p>
       <ul>
         <li><span>Rishab Manocha</span><span>300.139.589</span></li>
         <li><span>Cameron Carnegie</span><span>300.079.025</span></li>
       </ul>
       <p>Feel free to contact us at our emails:
         <ul>
           <a href="mailto:rishab.manocha@student.ufv.ca">Rishab Manocha</a>
           <a href="mailto:cameron.carnegie@student.ufv.ca">Cameron Carnegie</a>
         </ul>
       </p>
     </div>
    </main>
    <footer>
      <p>Copyright 2017</p>
      <p>RC Games</p>
      <p>Instructor: Shabnam Mirshokraie</p>
    </footer>
  </body>
</html>
