<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%-- <%@ page import="javax.servlet.http.HttpServletResponse" %> --%>
		<%-- <h1>Welcome <%= session.getAttribute("user") %> !</h1> --%>

				<%-- If I log out, then by pressing back button, I shouldn't have access to the page.
					${response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate" )} //HTTP 1.1
					${response.setHeader("Pragma", "no-cache" )} //HTTP 1.0 ${response.setHeader("Expires", "0" )}
					//Proxy Servers --%>

					<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate" );
						//response.setHeader("Pragma", "no-cache" ); //response.setHeader("Expires", "0" ); %>

						<!doctype html>
						<html lang="en">

						<head>
							<title>Digital Signature</title>
							<!-- Required meta tags -->
							<meta charset="utf-8">
							<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

							<!-- Bootstrap CSS v5.2.1 -->
							<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
								rel="stylesheet"
								integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
								crossorigin="anonymous">


							<style>
								#firsth1 {

									color: white;
									margin-top: 10px;

									font-size: x-large;
								}

								#row1col1 {
									display: flex;
									justify-content: space-evenly;
									height: 75px;

								}

								#img1 img {
									width: 140px;
									height: 100%;
								}

								#row1 {
									background-color: #212529;

								}



								#row2 {
									/* display: flex;
      								justify-content: space-evenly; */
									margin: 100px 2px 20px 2px;
									border: 1px solid darkslategray;

								}

								#upload1 {
									/* display: inline-block; */
									margin: 20px;
									text-align: left;
								}

								#upload2 {
									/* display: inline-block;  */
									margin: 20px;
									text-align: left;
								}

								#firsth2 {
									background-color: #212529;
									color: white;
									text-align: center;
									width: 500px;
									/* height: 80px; */
									border-radius: 10px;
								}

								#secondh2 {
									background-color: #212529;
									color: white;
									text-align: center;
									width: 500px;
									/* height: 80px; */
									border-radius: 10px;
								}
							</style>

						</head>

						<body>
							<header>
								<!-- place navbar here -->
							</header>
							<main>
								<div class="container-fluid">
									<div id="row1" class="row">
										<div id="row1col1" class=" col-12">


											<div id="img1">
												<img src="download (1).png">
											</div>
											<center id="firsth1">Welcome ${user} !</center>
											<div>
												<form action="logout" method="post">
													<button class="btn btn-primary btn-lg my-2">Logout</button>
												</form>
											</div>

										</div>
									</div>

									<!-- id="upload1" -->

									<div id="row2" class="row">
										<div class="col-lg-6 col-md-6 col-12">
											<div id="upload1">
												<h2>Upload here to request for a signature</h2>
												<form action="fileUpload" method="post" enctype="multipart/form-data">
													<input type="text" name="invited" required
														placeholder="Who is invided?"> <br>
													<input type="file" name="file" accept=".pdf" multiple required
														placeholder="Upload file" style="margin: 5px 0px 5px 0px;"> <br>
													<button class="btn btn-primary">submit</button>
												</form>

												<div style="border: 1px solid black; display: inline-block; margin-top: 5px;">
													Note: ${fileSavedOrInviteeSameAsInvited}
													${fileSavedOrInviteeSameAsInvited=null}
												</div>


											</div>
										</div>

										<div class="col-lg-6 col-md-6 col-12">
											<div id="upload2">
												<h2>Upload here to verify your signature</h2>
												<form action="signatureVerification" method="post"
													enctype="multipart/form-data">
													Signed PDF File :<input type="file" name="uploadedSignedFile"
														accept=".pdf" required> <br>
													Digital Signature ".txt" file:<input type="file" name="uploadedDigSigFile"
														accept=".txt" required style="margin: 5px 0px 5px 0px;"> <br>
													Signed by:<input type="text" name="signedBy" required> <br>
													<button class="btn btn-primary" style="margin: 5px 0px 5px 0px;">submit</button>
												</form>

												<div style="border: 1px solid black; display: inline-block;">
													Note: ${isSignatureMatched}
													${isSignatureMatched=null}
												</div>

											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-s-12" style="display: flex; justify-content: center;">
											<h2 id="firsth2">INCOMING SIGNATURE REQUEST</h2>
										</div>
									</div>


									<div id="row4" class="row">
										<div class="col-12" style="margin-top: 5px; ">

											<table class="table table-dark table-striped">
												<thead>
													<tr>
														<th>Who invited you?</th>
														<th>DocumentName</th>
														<th>Upload Time-stamp</th>
														<th>Enter keystore password and press "sign"</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="invitedInvitee" items="${indvList}">
														<tr>
															<td>${invitedInvitee.getInviteeName()}</td>
															<td>
																<form action="download" method="post">
																	<input type="hidden" value=${invitedInvitee.getId()}
																		name="rowID">
																	<button>Download</button>
																	${invitedInvitee.getFileName()}
																</form>
															</td>
															<td>${invitedInvitee.getTimeOfUpload()}</td>
															<td>
																<form action="sign" method="post">
																	<input type="password" name="signPass" required>
																	<input type="hidden" value=${invitedInvitee.getId()}
																		name="rowID">
																	<button>Sign</button>
																</form>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>

											
											<div style="border: 1px solid black; display: inline-block;">
												Note: ${alreadySigned}
												${alreadySigned=null}
											</div>

										</div>
									</div>

									<div class="row">
										<div class="col-s-12" style="display: flex; justify-content: center;">
											<h2 id="secondh2">OUTGOING SIGNATURE REQUEST</h2>
										</div>
									</div>



									<div class="row">
										<div class="col-12">
											<table class="table table-dark table-striped">
												</tbody>
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
											</table>
										</div>
									</div>
								</div>
							</main>
							<footer>
								<!-- place footer here -->
							</footer>
							<!-- Bootstrap JavaScript Libraries -->
							<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
								integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
								crossorigin="anonymous">
								</script>

							<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
								integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
								crossorigin="anonymous">
								</script>
						</body>

						</html>