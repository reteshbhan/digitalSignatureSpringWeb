package com.cdac.login.controller;

import java.io.Serializable;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.model.InvitedInvitee;
import com.cdac.login.service.digitalSignature.SignatureGenerationService;
import com.cdac.login.service.registerAndLoginService.KeyRetreivalService;
import com.cdac.login.service.registerAndLoginService.LoginDataStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController implements Serializable { 
	String username;
	@Autowired
	LoginDataStorageService loginDataStorageServiceObj;
	@Autowired
	KeyRetreivalService keyRetreivalServiceObj;
	@Autowired
	SignatureGenerationService signatureGenerationServiceObj;
	
	@RequestMapping("/")
	public String loginPage() {
		System.out.println("Inside /");
		return "loginPage.jsp";
	}
	
	@RequestMapping("/redir")
	public String redirection(HttpSession session) {
		if(username==null)
			return "redirect:/";
		//System.out.println(username);
		List<InvitedInvitee> ls = loginDataStorageServiceObj.getIndividualFilesList(username);
		session.setAttribute("indvList", ls);
		List<InvitedInvitee> ls1 = loginDataStorageServiceObj.getRequestedFilesList(username);
		session.setAttribute("reqList", ls1);
		
		return "homePage.jsp";
	}
	
	@RequestMapping("/verifyLogin") 
	public String verifyLogin(String v1, String v2, HttpSession session) {
		
		username = v1;
		if(v1 == null)
			return "redirect:/";
		else if(v1=="" || v2=="")
		{
			session.setAttribute("message", "Fields are null");
			return "loginPage.jsp";
		}
		else if(loginDataStorageServiceObj.confirmEmployeePresence(v1)) {
			session.setAttribute("message", "Employee not Found.");
			return "loginPage.jsp";
		}
		else if(!v1.equals(v2))
		{
			session.setAttribute("message", "Invalid username or password!");
			return "loginPage.jsp";
		}
		else
		{
			session.setAttribute("message", "");
			session.setAttribute("user", v1);
			
			System.out.println("Inside /verifyLogin");
			return "redirect:/redir"; 
		}	
	}
	
	@RequestMapping("/fileUpload")
	public String fileUpload(String invited, MultipartFile file, HttpSession session) {
		if(invited == null)
		{
//			System.out.println("Inside /fileUpload from top");
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
		
		System.out.println("Inside /fileUpload");
		
		return "redirect:/redir";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		username = null;	//Very Important. /Redir was giving issues. This will solve it.
		return "redirect:/";
	}
	
	@RequestMapping("/sign")
	public String signature(Integer rowID) {
		//System.out.println("in sign controller. Row = " + rowID);
		
		/** CODE TO RETREIVE THE KEY-FILE FROM "EmployeeRegistration" TABLE.*/
		byte[] keyFileInBytes = keyRetreivalServiceObj.keyObjectRetreival(username);
		
		/** CODE TO RETREIVE THE UNSIGNED-FILE FROM "InvitedInvitee" TABLE.*/
		byte[] unsignedFileInBytes = loginDataStorageServiceObj.getUnsignedFile(rowID);
		
		//CODE TO REMOVE THE ROW FROMT THE TABLE.
		
		signatureGenerationServiceObj.digitalSignature(keyFileInBytes, unsignedFileInBytes);
		signatureGenerationServiceObj.digitalSignatureVerification(keyFileInBytes, unsignedFileInBytes);
		
		return "redirect:/redir";
	}
}


