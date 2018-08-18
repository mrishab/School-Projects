//script contain the functions js functions

// this function is used for sending the data filled by user during registration.
function sendData() {
  event.preventDefault();
    var fields = document.getElementsByTagName('input');
    var user = makeUser(fields); // makes an object with all input field properties
    var xtp = new XMLHttpRequest();
    var pop = document.getElementById("popup");
    xtp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            pop.innerHTML = this.responseText
            togglePop(pop);
            if(this.responseText == "User registered successfully"){
              pop.style="font-size:110%";
              setTimeout(function(){
                window.location.href = "index.php";
              },3000);
            }
        }
    }

    xtp.open("POST", "assets/script/php/register.php", true);
    xtp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    //the above content tpe is form so that json string can be obtained from the $_post[] variable on the server side
    // this helps in avoiding problems associated with the getting the contents of the json string
    xtp.send("user=" + user); // send the user object in json format
}
//***********************************************************************************
//Login Version of the send data function. Only difference is php file location
function sendLoginData() {
  event.preventDefault();
    var fields = document.getElementsByTagName('input');
    var user = makeUser(fields);
    var xtp = new XMLHttpRequest();
    var pop = document.getElementById("popup");
    xtp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          if(this.responseText == "User Authenticated"){
            window.location.href = "home.php";
          }
          else{
            pop.innerHTML = this.responseText
            togglePop(pop);
          }
        }
    }

    xtp.open("POST", "assets/script/php/login.php", true);
    xtp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xtp.send("user=" + user);
}
//*************************************************************************************

//this function takes all the input fields and make a object which is then converted to the json string and returned
function makeUser(arr) {
    var user = new Object();
    var property, value;

    for (var i = 0; i < arr.length; i++) { // checking for radio types input and selecting only the checked ones
        if(arr[i].type == "radio"){ //check if the input type is radio
          if(arr[i].checked){ // if it is of the radio type, see if it is checked or not
            value = arr[i].value; // it only assigns the value of checked radio button
          }
          else{
            continue; // if it is of type radio, but not checked, then it is skipped
          }
        }
        property = arr[i].name; // in other cases, the value of input fields is directly assigned to the object
        value = arr[i].value;
        user[property] = value;
    }
    //
    user = JSON.stringify(user); // user object is stringified and returned.
    return user;
}

//toggle popup function to show and hide pop up messages
function togglePop(obj){
  if (obj.className == "hide")
    obj.className = "show";
  setTimeout(function(){
    obj.className = "hide";
  }, 3000);
}
