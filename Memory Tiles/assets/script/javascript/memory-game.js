/*
this script contains code for memory tiles game.
this game is made specifically for this project.
It is not copied or retrieved from any other source in any way.
*/

var frame = document.getElementById("game-frame");
var status = document.getElementById("status");
const GAME_SIZE = 40; // number of tiles
var counter = 0, moves = 0, match = [], solved = [];
var bgList = createBgList();
//the above list contains the links to the images for the tiles.

createGame(frame);
setVal(frame, bgList)

function createBgList(){
  var list = [];
  for(var i = 1; i <= (GAME_SIZE/2); i++) // the number of images is half the total number of tiles.
    list.push("assets/images/memory-game/" + i + ".png"); // the images are numbered
  return list;
}

//the function flips a tile and reveals the image underneath it.
function flip(obj){
  $(obj).slideUp().slideDown().css("background-image", "url(" + bgList[obj.value] + ")");
  obj.removeEventListener('click', play); // when the image is flipped up, it no longer can be clicked
  // so the click event listener is removed
  counter++;
  moves++;
  $("#status").text("Moves: " + moves);
}

function unFlip(obj){ // if tile doesn't match, it is unflipped
  $(obj).slideUp().slideDown().css("background-image", "url('assets/images/memory-game/default.png')");
  obj.addEventListener('click', play); // the listener is added back to restore onlick functionality
}

function play(){ //function guides one round of game play which consists of flip and unflip
  if(counter < 2){ // counts the number of tiles which are flipped which cannot be more than 2 at a time
    match[counter] = this; // stores the flipped tile at the respective index in the array
    flip(this); // reveals the image
    status.innerHTML = "Moves: " + moves;
    if(counter == 2){ // if two images have been flipped open
      if(match[0].value != match[1].value){ // it matches there value
        setTimeout(function(){ // if values are different
          unFlip(match[0]); // it unflips both of them back
          unFlip(match[1]);
          match = [];
          counter = 0; // and resets the flip counter
        }, 2000); // images are displayed for 2 seconds so that user can memorise them
      }
      else{ // in case they are a match, their values is stored in the solved array and they are not unflipped
        solved.push(match[0]);
        solved.push(match[1]);
        match = [];
        counter = 0; // counter still needs to be reset to 0.
      }
    }
  }
}
// NOTE: the function below do not adds to the functionality of the game, it only saves the manual efforts and provides scope for scalability of the game
function createTile(){ // creates a tile with given classname and event listener.
  var button = document.createElement('BUTTON');
  button.className = "memory-tile";
  button.addEventListener('click', play);
  return button;
}

// NOTE: the function below do not adds to the functionality of the game, it only saves the manual efforts and provides scope for scalability of the game
function createGame(frame){ // creates the game area by looping on the createTile function
  for(var i = 0; i < GAME_SIZE; i++)
    frame.appendChild(createTile());
}

// NOTE: the function below do not adds to the functionality of the game, it only saves the manual efforts and provides scope for scalability of the game
// this function makes a list of tile indices. the list is need for assigning unqiue indices to the tiles
function mkTileIndice(){
  var tileIndice = []
  for(var i = 0; i < GAME_SIZE; i++)
    tileIndice.push(i);
  return tileIndice;
}

//this function assigns a random value to each tile and make sures that each tile has a matching tile as well
function setVal(frame, bgList){
  var tileIndice = mkTileIndice();
  var tile = frame.children;
  var rand, rand2;

  for(var i = 0; i < GAME_SIZE/2; i++){
    rand = Math.floor(Math.random()*tileIndice.length); // gen a rand number
    tile[tileIndice[rand]].value = i; // assign value of iteration to the random tile
    tileIndice.splice(rand,1); // removes that tile index from the list so that same tile is not referred to again.

    rand2 = Math.floor(Math.random()*tileIndice.length); // gen a second rand number
    tile[tileIndice[rand2]].value = i; // assign the same iteration value to a random tile
    tileIndice.splice(rand2,1); //remove that tile index as well

    //so now there are two random tiles with same value assigned to them.
  }
}

/*EXTRA FEATURES*/
var resetB = document.getElementById("reset");
var submitB = document.getElementById("submit");

resetB.addEventListener('click', reset);
submitB.addEventListener('click', submit);

function submit(){ // if the user wants to if he matched all the tiles or not.
  var child = frame.children;
  var diff = $(child).not(solved).get();

  if(diff.length != 0){
    $("#status").text("Sorry. You didn't match all the tiles");
    for(var i = 0; i < diff.length; i++)
      diff[i].style.border = "2px solid red";
  }
}

function reset(){
  while(frame.firstChild)
    frame.removeChild(frame.firstChild);
    counter =0;
    moves= 0;
    status.innerHTML = "Moves: " + moves;
  createGame(frame);
  setVal(frame, bgList);
}
