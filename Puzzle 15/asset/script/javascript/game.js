var solution = []; // stores the solution for the puzzle.
createPuzzle(); // calling the function to create the puzzle game.
randomise(); // Calling the function to randomise the tiles into a solvable puzzle.
var count = 0; // counts the number of moves made by the player

function createPuzzle(){
  var frame = document.getElementById("frame");
  var empty;
  for(var x = 1; x < 16; x++){
    var tile = document.createElement("BUTTON");
    tile.id="tile-" + x;
    tile.appendChild(document.createTextNode(x));
    tile.addEventListener('click', chngTiles);
    frame.appendChild(tile);
  }
  //Creating an empty button-space for swapping
  empty = document.createElement("BUTTON");
  empty.id = "empty";
  empty.appendChild(document.createTextNode("empty"))
  frame.appendChild(empty);
  //NOTE: THIS BUTTON WILL BE MADE INVISIBLE IN CSS.
}

function chngTiles(){

  var selected = document.getElementById(this.id); //gets the button clicked
  var empty = document.getElementById("empty"); // gets the empty space (button)

  var empInd = findIndex(empty); // get index value for the empty space
  var selInd = findIndex(selected); // gets the index value for the clicked item
  var diff = selInd-empInd;
  var shouldSwap = false;
// The below checks the tiles are adjacent either vertically or horizontally
    if(diff == 4 || diff == -4)
      shouldSwap = true;
    else if(diff == 1 && selInd%4 != 0)
      shouldSwap = true;
    else if(diff == -1 && selInd%4 != 3)
      shouldSwap = true;
    if(shouldSwap){
      count++;
      document.getElementById('count').innerHTML = "Moves: " + count;
      swap(selected, empty); // it interchanges their places.
    }
}

function swap(obj1, obj2){ // function swaps any two given elements
  var temp = obj2.cloneNode(true); // clones the second object, which is probably the empty space.
  obj1.parentNode.insertBefore(temp, obj1); // copies the cloned empty space before the item clicked.
  obj2.parentNode.replaceChild(obj1, obj2); // then replaces the empty space with the item that was clicked
  //overall effect, they both get swapped
}

function findIndex(element){ // function to find the index of any given element
  var children = element.parentNode.children; // makes a list of all the siblings
  var index;
  for(var x = 0; x < children.length; x++) // loops through the siblings
    if(children[x] == element){ // if any element in the list matched the specified element,
      index = x; // it notes its place index
      break;
    }
  return index; // and then returns it
}
//---------------------SOLVABLE CONFIGURATIONS----------------------------------
function randomise(){ // This function plays random moves on the correctly ordered puzzle to make it random
  var list = [1,-4,-1,4] // tile indices horizontal or vertical to the empty tile.
  var shouldSwap, empty, empInd, rand, selInd, change, diff;

  for(var x = 0; x < 300; x++){ // randomly swaps the tiles 300 times
    shouldSwap = false;
    empty = document.getElementById("empty");
    empInd = findIndex(empty);

    do{
      rand = Math.floor(Math.random()*list.length,0);
      diff = list[rand];
      selInd = empInd + diff;
      if(diff == 1 && selInd%4 != 0)
        shouldSwap = true;
      else if(diff == -1 && selInd%4 != 3)
        shouldSwap = true;
      else if (diff == 4 || diff == -4)
        shouldSwap = true;
      if(shouldSwap)
        change = document.getElementById("frame").children[selInd];
      if(change == null) // if no tile selected is null, do it over
        shouldSwap = false;
      }
    while(!shouldSwap);
    swap(change,empty);
    solution.push(change); // makes list of tiles swapped made randomly
  }
  solution.reverse(); // reverse the list so that most recent change appears first
  console.log(solution); // view the console to see the solution.
}

//-------------------------------------EXTRA FEATURES---------------------------------------------------------------
var resetButton = document.getElementById("reset");
var submit = document.getElementById("submit");

resetButton.addEventListener('click', reset);
submit.addEventListener('click', check);

function reset(){// clicking on this resets the game
  console.clear();
  solution = [];
  var frame = document.getElementById("frame");
  while(frame.firstChild) // removes any previous child the frame has.
    frame.removeChild(frame.firstChild);
  document.getElementById("count").innerHTML = "Moves: 0";
  createPuzzle();
  randomise();
}
function check(){//clicking on this checks whether you won or not
  var correct = true;
  var list = document.getElementById("frame").children;
  for(var x = 0; x < list.length-1; x++){
    var childInd = list[x].id;
    if(childInd != ("tile-" + (x+1))){
      correct = false;
      break;
    }
  }
  if(correct){
    document.getElementById("count").innerHTML = "You Won!"
  }
  else{
    document.getElementById("count").innerHTML = "You Failed :'(";
  }
}
