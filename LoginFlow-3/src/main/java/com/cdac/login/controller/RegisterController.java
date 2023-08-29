package com.cdac.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.service.digitalSignature.KeyStoreAndKeyAndCertificateGenerationService;
import com.cdac.login.service.registerAndLoginService.FileRemovalService;
import com.cdac.login.service.registerAndLoginService.LoginDataStorageService;
import com.cdac.login.service.registerAndLoginService.RegistrationDataStorageService;

import jakarta.servlet.http.HttpSession;

//"webAppUserName" is same as "username".

@Controller
public class RegisterController
{
	@Autowired
	private RegistrationDataStorageService registrationDataStorageServiceObj;
	@Autowired
	private KeyStoreAndKeyAndCertificateGenerationService keyStoreAndKeyAndCertificateGenerationServiceObj;
	@Autowired
	private LoginDataStorageService loginDataStorageServiceObj;
	@Autowired
	private FileRemovalService fileRemovalServiceObj;
	  
	@RequestMapping("/create")
	public String registrationPage()
	{
		return "index.jsp";
	}
	
	@RequestMapping("/registration")
	public String savingDetails(EmployeeRegistration employeeRegistrationObj, HttpSession session, String confirmPassword, String confirmKeystorePassword)
	{
		if(employeeRegistrationObj.getEmpName() == "" || employeeRegistrationObj.getWebAppPassword()==""|| employeeRegistrationObj.getWebAppUserName()== "" || employeeRegistrationObj.getKeystorePassword()=="")
		{
			session.setAttribute("message","please enter every value above.");
			return "redirect:/create";
		}
		else if(!loginDataStorageServiceObj.confirmEmployeePresence(employeeRegistrationObj.getWebAppUserName())) {
			session.setAttribute("message","Employee already in data base.");
			return "redirect:/create";
		}
		else if(!confirmPassword.equals(employeeRegistrationObj.getWebAppPassword()) || !confirmKeystorePassword.equals(employeeRegistrationObj.getKeystorePassword()) ) {
			session.setAttribute("message","All passwords are not matching");
			return "redirect:/create";
		}
		else
		{
			//Code for softHSM
			List<MultipartFile> multipartFileList = keyStoreAndKeyAndCertificateGenerationServiceObj.keyStoreKeyCertificateGeneration(employeeRegistrationObj);
			
			if(!multipartFileList.isEmpty())
				registrationDataStorageServiceObj.createEmployee(employeeRegistrationObj,multipartFileList);
			
			/** REMOVING GENERATED KEY-STORE BECAUSE ALREADY SAVED IN DATABASE*/
			fileRemovalServiceObj.removeGeneratedKeyStoreFile(employeeRegistrationObj.getWebAppUserName());
			return "token.jsp";
		}
	}
	
  
}
