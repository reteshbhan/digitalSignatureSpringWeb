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
		Upload file:<input type="file" name="file" accept=".pdf" multiple required> <br>
		<button>Submit</button>
		</form>
	
		<%-- Note: <%= session.getAttribute("message") %> --%>
		Note: ${fileSavedOrInviteeSameAsInvited}
		${fileSavedOrInviteeSameAsInvited=null}
	</div>
</div>

<div style="border: 2px solid black; margin: 20px; display: inline-block">
	<div style="display: inline-block; margin: 20px;">
		<h2>Upload here to verify a signature</h2>
		<form action="signatureVerification" method="post" enctype="multipart/form-data">
		Signed PDF File	:<input type="file" name="uploadedSignedFile" accept=".pdf" required> <br>
		Signature file:<input type="file" name="uploadedDigSigFile" accept=".txt" required> <br>
		Signed by:<input type="text" name="signedBy" required> <br>
		<button>Verify</button>
		</form>
	
		<%-- Note: <%= session.getAttribute("message") %> --%>
		Note: ${isSignatureMatched}
		${isSignatureMatched=null}
	</div>
</div>

<br>

<div style="border: 1px solid black; margin: 20px;  display: inline-flex;">
	<div style="display: inline-block; border: 2px solid black; margin: 20px; padding: 20px;">
		<h2>Incoming signature requests</h2>
		<table border="2" style="color:black">
			<thead>
				<tr>
					<th>Who invited you?</th>
					<th>DocumentName</th>
					<th>Upload Time-stamp</th>
					<th>Enter password and press "sign"</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="invitedInvitee" items="${indvList}">
		        <tr>
		            <td>${invitedInvitee.getInviteeName()}</td>
		            <td>
						<form action="download" method="post">
							<input type="hidden" value= ${invitedInvitee.getId()} name="rowID" >
							<button>Download</button>   ${invitedInvitee.getFileName()}
						</form>
					</td>
					<td>${invitedInvitee.getTimeOfUpload()}</td>
		            <td>
						<form action="sign" method="post">
							<input type="password" name="signPass" required>
							<input type="hidden" value= ${invitedInvitee.getId()} name="rowID" >
							<button>Sign</button>
						</form>
					</td>
		        </tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		Note: ${alreadySigned}
		${alreadySigned=null}
	</div>
	
	<div style="display: inline-block; border: 2px solid black; margin: 20px; padding: 20px;">
		<h2>Outgoing signature requests</h2>
		<table border="2" style="color:black">
			<thead>
				<tr>
					<th>Whom you invited?</th>
					<th>DocumentName</th>
					<th>Upload Time-stamp</th>
					<th>Signature done?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="invitedInvitee" items="${reqList}">
		        <tr>
		            <td>${invitedInvitee.getInvitedName()}</td>
		            <td>${invitedInvitee.getFileName()}</td>
		            <td>${invitedInvitee.getTimeOfUpload()}</td>
		            <td>${invitedInvitee.isSigned()}</td>
		        </tr>
				</c:forEach>
			</tbody>
		</table>
	</div style="border: 2px solid black;">
</div>






