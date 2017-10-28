<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<style>
#tab {
	display: inline-block;
	margin-left: 40px;
}

canvas {
	color: black;
	border: black dashed 2px;
	padding: 15px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HangMan</title>
</head>
<body>
	<h1>Welcome to HangMan Game</h1>
	<h2>Click the Letter or Press in KeyBoard</h2>
	<hr>
	<h1>Total Wins : ${sessionScope.wins}</h1>
	<h1>Total Lost : ${sessionScope.loses}</h1>
	<hr>
	<h1>Guess : ${sessionScope.output}</h1>
	<h2>
		Misses: <span id="missId">${sessionScope.missCount}</span>
	</h2>
	<h2>
		<c:forEach items="${sessionScope.charSet}" var="c">
			<span id="tab"> <a href="/home/${c}" id="link${c}"> ${c} </a>
			</span>
		</c:forEach>
	</h2>
	<form>
		<c:if test="${won==true}">
			<script>
				var actualWord= "${sessionScope.word}";
				setTimeout(function() { alert("You won the game \n The word is "+actualWord); }, 500);
				setTimeout(function(){location.href="/newGame"} , 510);
			</script>
		</c:if>
		<p id="demo"></p>
	</form>
	<canvas id="stickman">This Text will show if the Browser does NOT support HTML5 Canvas tag</canvas>
	<script>
	window.onload = function(){
			// Hangman
		    canvas =  function(){
		    myStickman = document.getElementById("stickman");
		    context = myStickman.getContext('2d');
		    context.beginPath();
		    context.strokeStyle = "black";
		    context.lineWidth = 2;
		  };
		  canvas();
		
		 draw = function($pathFromx, $pathFromy, $pathTox, $pathToy) {
			    context.moveTo($pathFromx, $pathFromy);
			    context.lineTo($pathTox, $pathToy);
			    context.stroke(); 
			}
		 head = function(){
		      myStickman = document.getElementById("stickman");
		      context = myStickman.getContext('2d');
		      context.beginPath();
		      context.arc(60, 25, 10, 0, Math.PI*2, true);
		      context.stroke();
		    }
			   frame1 = function() {
			     draw (0, 150, 150, 150);
			   };
			   
			   frame2 = function() {
			     draw (10, 0, 10, 600);
			   };
			  
			   frame3 = function() {
			     draw (0, 5, 70, 5);
			   };
			  
			   frame4 = function() {
			     draw (60, 5, 60, 15);
			   };
			  
			   torso = function() {
			     draw (60, 36, 60, 70);
			   };
			  
			   rightArm = function() {
			     draw (60, 46, 100, 50);
			   };
			  
			   leftArm = function() {
			     draw (60, 46, 20, 50);
			   };
			  
			   rightLeg = function() {
			     draw (60, 70, 100, 100);
			   };
			  
			   leftLeg = function() {
			     draw (60, 70, 20, 100);
			   };
			   console.log(rightLeg);
			   drawArray = [rightLeg, leftLeg, rightArm, leftArm,  torso,  head, frame4, frame3, frame2, frame1]; 
			  
		 // Animate man
		  var animate = function () {
		    var drawMe = ${sessionScope.drawCount};
		    var i;
		    for(i=drawMe;i<=9;i++){
		    	drawArray[i]();	
		    }
		  }
		 
		  var value = document.getElementById("missId").innerHTML;
			 if(value.trim() > 0 && value.trim()<=10){
				 animate(); 
			 }
			 if(${lost==true}){
				 //alert(actualWord);
				 var actualWord= "${sessionScope.word}";
				 setTimeout(function() { alert("You lost the game \n The word is "+actualWord); }, 500);
				 setTimeout(function(){location.href="/newGame"} , 510);
			 } 
	}
	
	// J Query to accept keyboard letters
	$(document).ready(function() {
		   $("body").keypress(function(event) {
		    var link = "#link";
		    var key=event.keyCode;
		    var letter=String.fromCharCode(key).toLowerCase();
		    link+=letter;
		      if(link != "#link") {
			  $(link)[0].click();
		      }
		   });
		});

	</script>
</body>
</html>