package com.cdac.login.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.InvitedInvitee;
import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;
import com.cdac.login.service.digitalSignature.KeyRetreivalService;
import com.cdac.login.service.digitalSignature.SignatureGenerationService;
import com.cdac.login.service.registerAndLoginService.LoginDataStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController{ 
	private String username;		// "webAppUserName" is same as "username".
	@Autowired
	private LoginDataStorageService loginDataStorageServiceObj;
	@Autowired
	private KeyRetreivalService keyRetreivalServiceObj;
	@Autowired
	private SignatureGenerationService signatureGenerationServiceObj;
	@Autowired
	private DigitalSignatureJpaLoginRepositoryDAO daoObj;
	
	@RequestMapping("/")
	public String loginPage() {
		return "loginPage.jsp";
	}
	
	@RequestMapping("/redir")
	public String redirection(HttpSession session) {
		if(username==null)
			return "redirect:/";
		List<InvitedInvitee> ls = loginDataStorageServiceObj.getIndividualFilesList(username);
		session.setAttribute("indvList", ls);
		List<InvitedInvitee> ls1 = loginDataStorageServiceObj.getRequestedFilesList(username);
		session.setAttribute("reqList", ls1);
		
		return "homePage.jsp";
	}
	
	@RequestMapping("/verifyLogin") 
	public String verifyLogin(String user, String password, HttpSession session) {
		
		username = user;
		if(user == null)
			return "redirect:/";
		else if(user=="" || password=="")
		{
			session.setAttribute("message", "Fields are null");
			return "loginPage.jsp";
		}
		else if(loginDataStorageServiceObj.confirmEmployeePresence(user)) {
			session.setAttribute("message", "Employee not Found.");
			return "loginPage.jsp";
		}
		else if(!password.equals(loginDataStorageServiceObj.getPassword(user)))
		{
			session.setAttribute("message", "Invalid username or password!");
			return "loginPage.jsp";
		}
		else
		{
			session.setAttribute("message", "");
			session.setAttribute("user", user);
			
			return "redirect:/redir"; 
		}	
	}
	
	@RequestMapping("/fileUpload")
	public String fileUpload(String invited, MultipartFile file, HttpSession session) {
		if(invited == null)
		{
			session.setAttribute("fileSavedOrInviteeSameAsInvited", "Invited can't be null.");
		}
		else if(invited.equals(username)) {
			session.setAttribute("fileSavedOrInviteeSameAsInvited", "Invitee can't be invited.");
		}
		else if(loginDataStorageServiceObj.confirmEmployeePresence(invited)) {
			session.setAttribute("fileSavedOrInviteeSameAsInvited", "Employee not Found.");
		}
		else {
			loginDataStorageServiceObj.uploadFile(invited, file, username);
			session.setAttribute("fileSavedOrInviteeSameAsInvited", "File Saved Successfully.");
		}
		
		return "redirect:/redir";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		username = null;	//Very Important. /Redir was giving issues. This will solve it.
		return "redirect:/";
	}
	
	@RequestMapping("/sign")
	public String signature(String signPass, Integer rowID, HttpSession session) {
		if(daoObj.isDocumentAlreadySigned(rowID)) {
			session.setAttribute("alreadySigned","Document is already signed");
			return "redirect:/redir";
		}
		else if(signPass == "")
		{
			session.setAttribute("alreadySigned","Password mandatory!!");
			return "redirect:/redir";
		}
		else if(!daoObj.keyStorePassword(username).equals(signPass)) {
			session.setAttribute("alreadySigned","Password incorrect!!");
			return "redirect:/redir";
		}
		
		/** CODE TO RETREIVE THE KEY-FILE AND PUBLIC-KEY FILE FROM "EmployeeRegistration" TABLE.*/	
		List<byte[]> list = keyRetreivalServiceObj.keyStoreAndPublicKeyFileRetreival(username);
		byte[] byteKeyStoreFile = list.get(0);
//		byte[] bytePublicKeyFile = list.get(1);
		
		/** CODE TO RETREIVE THE UNSIGNED-FILE FROM "InvitedInvitee" TABLE.*/
		byte[] unsignedFileInBytes = loginDataStorageServiceObj.getUnsignedFile(rowID);
		
		//CODE TO REMOVE THE ROW FROMT THE TABLE.
		
		signatureGenerationServiceObj.digitalSignature(byteKeyStoreFile, unsignedFileInBytes, username, rowID, signPass);
		//signatureGenerationServiceObj.digitalSignatureVerification(bytePublicKeyFile, unsignedFileInBytes, username);
		
		session.setAttribute("alreadySigned","Signing done and mail sent.");
		return "redirect:/redir";
	}
	
	/* Q) I have to download a file from mySql database using spring web. What is the common path of downloads folder
	 * in every windows machine?
	 * The common path for the downloads folder on Windows machines is typically:
	 * C:\Users\<YourUsername>\Downloads
	 * Replace <YourUsername> with your actual Windows username.
	 * 
	 * However, when you're developing a Spring web application to download a file from a MySQL database, you don't
	 * need to worry about the specific user's downloads folder. Instead, you'll be serving the file for download
	 * directly from your web application.
	 * 
	 * In a Spring web application, when you set up a file download functionality, the user will initiate the
	 * download by clicking a link or button on your web page. Your Spring application will handle the request,
	 * retrieve the file from the database, and provide it to the user as a downloadable file through the browser.
	 * The file won't actually be saved to the user's downloads folder on their machine unless they choose to save
	 * it there after downloading.
	 * 
	 * 
	 * Q) What is MIME type? 
	 * A MIME (Multipurpose Internet Mail Extensions) type is a standardized label that identifies the format and
	 * type of data in a file. It helps computers understand how to interpret and handle files by indicating their
	 * nature, such as text, images, audio, or video. MIME types are crucial for web communication, email
	 * attachments, and determining how browsers should handle different file types.
	 * MIME types consist of two parts:
	 * 		Media Type: This part specifies the general category of the data, such as text, image, audio, video, application, etc.
	 * 		Subtype: This part further refines the nature of the data within the given media type. For example, for the "text" media type, common subtypes include "plain," "html," "css," and so on.
	 * The combination of media type and subtype gives a complete MIME type. For example:
	 * 		"text/plain": Indicates plain text data.
	 * 		"image/jpeg": Indicates JPEG image data.
	 * 		"audio/mp3": Indicates MP3 audio data.
	 * 		"application/pdf": Indicates PDF document data.
	 * 
	 * 
	 * Q) content disposition
	 * Content-Disposition is an HTTP header that indicates how a server response should be handled by the
	 * recipient. It has two common types:
	 * 		1. **Inline:** `Content-Disposition: inline`
	 * 		This type suggests that the content should be displayed in the browser if possible. It's often used for images, videos, and other media directly viewable in the browser.
	 * 		2. **Attachment:** `Content-Disposition: attachment; filename="example.pdf"`
	 * 		This type prompts the browser to download the content as a file rather than displaying it. The "filename" parameter suggests the default name for the downloaded file.
	 * The header helps control how files are handled by web browsers, email clients, and other applications.
	 * */
	@RequestMapping("/download")
	/* chrome://settings/content/pdfDocuments*/
	public ResponseEntity<ByteArrayResource> fileDownload(Integer rowID){
		Optional<InvitedInvitee> fileRwoObj = daoObj.getUnsignedFileRowForDownload(rowID);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/pdf"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileRwoObj.get().getFileName()+"\"")
				.body(new ByteArrayResource(fileRwoObj.get().getDigitallyUnsignedFile()));
		
	}
	
	@RequestMapping("signatureVerification")
	public String signatureVerification(MultipartFile uploadedSignedFile, MultipartFile uploadedDigSigFile, String signedBy, HttpSession session) {
		if(uploadedSignedFile.isEmpty() || uploadedDigSigFile.isEmpty() || signedBy=="") {
			session.setAttribute("isSignatureMatched", "Complete all files");
			return "redirect:/redir";
		}
		else if(loginDataStorageServiceObj.confirmEmployeePresence(signedBy)) {
			session.setAttribute("isSignatureMatched", "Employee not Found.");
			return "redirect:/redir";
		}
		
		/** CODE TO RETREIVE THE PUBLIC-KEY FILE FROM "EmployeeRegistration" TABLE.*/	
		List<byte[]> list = keyRetreivalServiceObj.keyStoreAndPublicKeyFileRetreival(signedBy);
		int match = 0;
		byte[] bytePublicKeyFile = list.get(1);
		try {
			match = signatureGenerationServiceObj.digitalSignatureVerification(bytePublicKeyFile, uploadedSignedFile.getBytes(), username, uploadedDigSigFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(match == 1)
			session.setAttribute("isSignatureMatched", "Signature is VALID");
		else if(match == 2)
			session.setAttribute("isSignatureMatched", "Signature is INVALID");
		else
			session.setAttribute("isSignatureMatched", "Signature file INVALID");
		
		return "redirect:/redir";
	}
	
}


