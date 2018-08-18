var input = document.getElementsByClassName("signup-input");
input[0].addEventListener('change', valName);
input[1].addEventListener('change', valEmail);
input[2].addEventListener('change', valDob);
input[3].addEventListener('change', valPass);
function validInput(){
  var field = getField

}

function getField(find){
  for (var i = 0; i < input.length; i++)
    if(input[i].type == find){
      return input[i].value;
      break;
    }
}

function valPass(){
  var pass = getField("password");
  if(pass.length <= 31)
    alert("Password is too small");
}

function valName(){
  var name = getField("text");
  var status = document.getElementById("name-status");
  if(name.length <= 1)
    alert("This name is too small");

  for(var i = 0; i < name.length; i++)
    if($.isNumeric(parseInt(name[i]))){
      alert("Name cannot have digits");
      break;
    }
}

function valDob(){
    var dob = getField("date");
    var status = document.getElementById("date-status");
    var diff = new Date().getFullYear() - new Date(dob).getFullYear();
    if(diff < 13)
      alert("You need to be atleast 13 years old to sign up");
}
function valEmail(){
  var email = getField("email");
  var status = document.getElementById("email-status");
  if(email.length <= 1)
    alert("This email is invalid");
}
