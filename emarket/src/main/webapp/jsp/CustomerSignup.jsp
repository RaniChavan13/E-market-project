<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer SignUp</title>
</head>
<body>
<div class="header"></div>

<div class="container">
${fail}
<form action="/customer/signup" method="post">
<input type="text" name="name" placeholder="Name"> <br>
<input type="text" name="email" placeholder="Email"><br>
<input type="tel" name="mobile" pattern="{0-9}[10]" placeholder="Mobile"><br>
<input type="password" name="Password" placeholder="Create Password"> <br>
<input type="date" name="date" placeholder="Date Of Birth"> <br>
<input type="radio" name="gender" value="male">Male
<input type="radio" name="gender" value="female">Female <br> 
<textarea rows="5" cols="30" placeholder="Address"></textarea><br>
<button>Signup</button>
<button type="reset">Cancel</button>
</form>
<br><a href="/customer/login"><button>Back</button></a>
</div>

<div class="footer"></div>

</body>
</html>