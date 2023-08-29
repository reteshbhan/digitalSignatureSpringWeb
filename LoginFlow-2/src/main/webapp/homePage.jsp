<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="javax.servlet.http.HttpServletResponse" %>  --%>

<%-- <h1>Welcome <%= session.getAttribute("user") %> !</h1> --%>

<%-- If I log out, then by pressing back button, I shouldn't have access to the page. 
${response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")}	//HTTP 1.1
${response.setHeader("Pragma", "no-cache")}	//HTTP 1.0
${response.setHeader("Expires", "0")}	//Proxy Servers --%>

<% 
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Expires", "0");
%>

<div style="float: right;">
	<form action="logout" method="post">
		<button>Logout</button>
	</form>
</div>

<h1>Welcome ${user} !</h1>

<div style="border: 2px solid black; margin: 20px; display: inline-block">
	<div style="display: inline-block; margin: 20px;">
		<h2>Upload here to request for a signature</h2>
		<form action="fileUpload" method="post" enctype="multipart/form-data">
		Invited	:<input type="text" name="invited" required> <br>
		Upload file:<input type="file" name="file" required> <br>
		<button>Submit</button>
		</form>
	
		<%-- Note: <%= session.getAttribute("message") %> --%>
		Note: ${fileSavedOrInviteeSameAsInvited}
	</div>
</div>


<div style="">
	<div style="display: inline-block; margin: 20px; border: 2px solid black; padding:20px">
		<h2>My pending signatures</h2>
		<table border="2" style="color:black">
			<thead>
				<tr>
					<th>Id</th>
					<th>inviteeName</th>
					<th>DocumentName</th>
					<th>isSigned</th>
					<th>Click to sign</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="invitedInvitee" items="${indvList}">
		        <tr>
		            <td>${invitedInvitee.getId()}</td>
		            <td>${invitedInvitee.getInviteeName()}</td>
		            <td>${invitedInvitee.getFileName()}</td>
		            <td>${invitedInvitee.isSigned()}</td>
		            <td>
						<form action="sign" method="post">
							<input  type="hidden" value= ${invitedInvitee.getId()} name="rowID" >
							<button>Sign</button>
						</form>
					</td>
		        </tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div style="display: inline-block; margin: 20px; border: 2px solid black; padding:20px">
		<h2>My pending requests</h2>
		<table border="2" style="color:black">
			<thead>
				<tr>
					<th>Id</th>
					<th>invitedName</th>
					<th>DocumentName</th>
					<th>isSigned</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="invitedInvitee" items="${reqList}">
		        <tr>
		            <td>${invitedInvitee.getId()}</td>
		            <td>${invitedInvitee.getInvitedName()}</td>
		            <td>${invitedInvitee.getFileName()}</td>
		            <td>${invitedInvitee.isSigned()}</td>
		        </tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>






