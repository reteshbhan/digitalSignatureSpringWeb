<h1> Welcome on Registration page</h1>
<form action="registration" method="post">
	<h2>
		Employee_Name:<input type="text" name="empName" required><br>
		<br>
		User_Name:<input type="text" name="webAppUserName" required><br>
		<br>
		Web Password:<input type="password" name="webAppPassword" required><br>
		<br>
		Confirm Web Password:<input type="password" name="confirmPassword" required><br>
		<br>
		KeyStore Password:<input type="password" name="keystorePassword" required><br>
		<br>
		Confirm Keystore Password:<input type="password" name="confirmKeystorePassword" required><br>
		<br>
		Email:<input type="text" name="email" required><br>
		<br>
		<input type="submit" value="SUBMIT" name="n1">
	</h2>
</form>


<%-- Note: <%=session.getAttribute("message") %> --%>

	Note: ${message}
	${message=null}

	<br>
	<br>
	<br>

	<a href="loginPage.jsp">Login Page</a>