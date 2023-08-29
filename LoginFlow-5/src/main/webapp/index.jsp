
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
								h1 {

									color: azure;
									background-color: #212529;
									text-align: center;
									justify-content: center;
									padding: 10px;

								}


								.dig {

									text-align: center;
									justify-content: center;
									background-color: #212529;
									color: azure;

								}

								.dig ul {
									list-style: none;

								}
							</style>

						</head>

						<body>
							<header>
								<!-- place navbar here -->
							</header>
							<main>

								<div id="carouselExampleCaptions" class="carousel slide">

									<div class="carousel-inner">
										<div class="carousel-item active ">
											<h1 style="margin-bottom: 0%;font-size: 30px;">WELCOME ON PKI BASED WEB
												APPLICATION</h1>
											<img src="code4.jpg" class="d-block w-100" alt="...">


											<div class="carousel-caption d-none d-md-block">

												<button type="button" class="btn btn-primary btn-lg "
													data-bs-toggle="modal" data-bs-target="#modalId1">
													register
												</button>
												<button type="button" class="btn btn-primary btn-lg "
													data-bs-toggle="modal" data-bs-target="#modalId2">
													login
												</button>

												<br>
												<p
													style="background-color: white;color: black; display: inline-block; margin-top: 30px; padding: 2px; border-radius: 5px; ">
													Note: ${message}
													${message=null}
												</p>

											</div>



											<div class="modal fade" id="modalId1" tabindex="-1"
												data-bs-backdrop="static" data-bs-keyboard="false" role="dialog"
												aria-labelledby="modalTitleId" aria-hidden="true">
												<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm"
													role="document">
													<div class="modal-content">
														<div class="modal-header">
															<h5 class="modal-title" id="modalTitleId"
																style=" width:400px;">Please fill
																registration form</h5>
															<button type="button" class="btn-close"
																data-bs-dismiss="modal" aria-label="Close"></button>
														</div>
														<div class="modal-body">
															<!-- ======================================================= -->
															<!-- REGISTERATION PAGE -->
															<form action="registration" method="post">
																<div class="form-row">

																	<div class="col my-3">
																		<input type="text" name="empName" required
																			class="form-control"
																			placeholder="Enter Employee Name">
																	</div>
																	<div class="col my-3">
																		<input type="text" name="webAppUserName"
																			required class="form-control"
																			placeholder="Enter Web-app UserName">
																	</div>
																	<div class="col my-3">
																		<input type="password" name="webAppPassword"
																			required class="form-control"
																			placeholder="Enter Web-app password">
																	</div>
																	<div class="col my-3">
																		<input type="password" name="confirmPassword"
																			required class="form-control"
																			placeholder="Confirm Web-app Password">
																	</div>
																	<div class="col my-3">
																		<input type="password" name="keystorePassword"
																			required class="form-control"
																			placeholder="Enter Keystore Password">
																	</div>
																	<div class="col my-3">
																		<input type="password"
																			name="confirmKeystorePassword" required
																			class="form-control"
																			placeholder="Confirm Keystore Password">
																	</div>
																	<div class="col my-3">
																		<input type="text" name="email" required
																			class="form-control"
																			placeholder="Enter email">
																	</div>


																	<button type="submit"
																		class="btn btn-primary">Submit</button>
																</div>
															</form>
															<!-- ======================================================= -->
															<br>
															<!-- Note: ${message}
															${message=null} -->
														</div>

													</div>
												</div>

											</div>
											<!-- Optional: Place to the bottom of scripts -->
											<script>
												const myModal1 = new bootstrap.Modal(document.getElementById('modalId1'), options)

											</script>


											<div class="modal fade" id="modalId2" tabindex="-1"
												data-bs-backdrop="static" data-bs-keyboard="false" role="dialog"
												aria-labelledby="modalTitleId" aria-hidden="true">
												<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm"
													role="document">
													<div class="modal-content">
														<div class="modal-header">
															<h5 class="modal-title" id="modalTitleId"
																style=" width:500px">Please enter login
																details</h5>
															<button type="button" class="btn-close"
																data-bs-dismiss="modal" aria-label="Close"></button>
														</div>
														<div class="modal-body">
															<!-- ======================================================= -->
															<!-- LOGIN PAGE -->
															<form action="verifyLogin" method="post">
																<div class="form-row">


																	<div class="col my-3">
																		<input type="text" name="user" required
																			class="form-control"
																			placeholder="Enter Web-app UserName">
																	</div>
																	<div class="col my-3">
																		<input type="password" name="password" required
																			class="form-control"
																			placeholder="Enter Web-app password">
																	</div>


																	<button type="submit"
																		class="btn btn-primary">Submit</button>
																</div>
															</form>
															<br>
															<!-- Note: ${message}
															${message=null} -->

															<!-- ======================================================= -->
														</div>

													</div>
												</div>
											</div>


											<!-- Optional: Place to the bottom of scripts -->
											<script>
												const myModal2 = new bootstrap.Modal(document.getElementById('modalId2'), options)

											</script>

							</main>
							<footer>
								<div class="col-md-4" style="margin:auto; text-align: center;">
									<img src="download (1).png"
										class="img-fluid rounded-start my-5  width:526px height:526px" alt="...">
								</div>


								<!-- PROJECT DESCRIPTION -->
								<div class="desc"
									style="text-align: center; justify-content: center; background-color:white; margin: auto; margin-bottom: 65px; width: 80%; ">




									<h3 class="card-title" style="margin-right: 10px;margin-bottom: 10px;">Project
										Description</h3>
									<p class="card-text" style="text-align:justify; justify-content: center;">In the
										realm of digital security,
										a digital signature serves as a crucial tool for ensuring the authenticity,
										integrity, and
										non-repudiation of digital documents or data. Employing cryptographic
										techniques, a digital signature
										binds a unique identifier to the content, verifying its origin and confirming
										that the data has not been
										altered during transmission. In the context of our Spring Boot program, we
										implemented this advanced
										security measure using a PKCS12 keystore, which stores cryptographic keys and
										certificates. By
										integrating the Bouncy Castle library, renowned for its cryptography
										capabilities, we were able to
										facilitate the process of signing and verifying digital signatures effectively.
										Furthermore, our usage
										of PDFBox, a reliable Java library for working with PDF documents, allowed us
										to seamlessly integrate
										the digital signature into PDF files, bolstering their authenticity and
										establishing trust among parties
										involved. This comprehensive approach demonstrates a commitment to secure
										communication and document
										management within our application, contributing to a more trustworthy digital
										environment.</p>



									<!-- CONTACT US -->
								</div>
								<div class="dig" style="padding: 2% 0% 2% 0%;">
									<h2>Contact us</h2>


									<div
										style="display: flex; justify-content: center; text-align: left; margin-top: 2%;">
										<div>
											<table>
												<thead>
													<th>PANKAJ KUMAR SOLANKI</th>
												</thead>
												<tbody>
													<tr>
														<td>+91-8435702844</td>
													</tr>
													<tr>
														<td><a
																href="https://github.com/8435702844/pki-based-web-application-">GitHub</a>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div style="margin-left: 6%;margin-right: 6%;">
											<table>
												<thead>
													<th>RETESH BHAN</th>
												</thead>
												<tbody>
													<tr>
														<td>+91-8830101567</td>
													</tr>
													<tr>
														<td><a
																href="https://github.com/reteshbhan/digitalSignatureSpringWeb.git">GitHub</a>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div>
											<table>
												<thead>
													<th>SHRIVASTAVA ARPAN AJAY</th>
												</thead>
												<tbody>
													<tr>
														<td>+91-7909414507</td>
													</tr>
													<tr>
														<td><a
																href="https://github.com/l0gan01/DigitalSignaturewithAssymetricKeys.git">GitHub</a>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
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


						<!-- ----------------------------------------------------------------------------------------------------------------------------------------- -->