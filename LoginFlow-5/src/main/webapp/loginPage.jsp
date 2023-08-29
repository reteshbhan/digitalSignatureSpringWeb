<h1>
	<p>Hello Welcome to Digi-Signature Portal.</p>
</h1>
<h2>	
	<p>Please enter your credentials for login.</p>
</h2>
<form action="verifyLogin" method="post">
	Username(Employee ID)	:<input type="text" name="user" required> <br>
	Password				:<input type="password" name="password" required> <br>
	<button>Submit</button>
</form>

<%-- Note: <%= session.getAttribute("message") %> --%>

Note: ${message}
${message=null}


