<h1> Welcome on Registration page</h1>
<form action="registration" method="post" >
	 <h2>
	 Employee_Name:<input type="text" name="empName" required><br>
	 <br>
	 User_Name:<input type="text" name="webAppUserName" required><br>
	 <br>
	 Password:<input type="password" name="webAppPassword" required><br>
	 <br>
	 token_name:<input type="text" name="softHsmTokenLabel" required><br>
	 <br>
	 tokengeneration_pin:<input type="text" name="softHsmUserPin" required><br>
	 <br>
	 
	 <input type="submit" value="SUBMIT" name="n1">
	 </h2>
</form>

<%-- Note: <%= session.getAttribute("message") %> --%>

Note: ${message}
${message=null}