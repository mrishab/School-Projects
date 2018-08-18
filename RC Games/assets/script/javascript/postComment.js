// the script contains the definition and implementation of function used to post comment on the website
var button = document.getElementById("comment-button"); // post button
var comment_li = document.getElementById("input-comment-list"); // list element containing paragraph for comment
var comment_area = document.getElementById("input-comment"); // input paragraph cotaining the comment string
var comments = document.getElementById("comment-list"); // list (ul) containg the list elements of all the comments.
//ul > li > div > p > comment (string).
button.addEventListener("click", postComment);

/*function for posting a comment
the function gets the value of comment via onclick method, sends it to the server via ajax.
the php processes the comments, makes an entry into the database and returns a div item
with a paragraph containing the posted comment.

the function then creates a list item and appends it with the returned div.

the list item is appended at the end of the comments list thereby making it visible to
the user.
*/
function postComment(){
  var comment = comment_area.value; // the comment string
  xtp = new XMLHttpRequest();
  xtp.onreadystatechange = function(){
    if (this.readyState == 4 && this.status == 200){
      var newComment = document.createElement("li");
      newComment.className = "comment";
      newComment.innerHTML = this.responseText;
      comments.insertBefore(newComment, comment_li);
      comment_area.value = ""; // after posting the comments, the value in the input field is reset
    }
  }
  xtp.open("POST", "assets/script/php/postComment.php", true);
  xtp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xtp.send("comment="+comment);
}
