package com.cdac.login.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.service.digitalSignature.TokenAndKeyGenerationService;
import com.cdac.login.service.registerAndLoginService.RegistrationDataStorageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController  implements Serializable
{
  @Autowired
  private RegistrationDataStorageService registrationDataStorageServiceObj;
  @Autowired
  TokenAndKeyGenerationService tokenAndKeyGenerationServiceObj;
  
  @RequestMapping("/create")
	public String registrationPage()
	{
		return "index.jsp";
	}
	
	@RequestMapping("/registration")
	public String savingDetails(EmployeeRegistration employeeRegistrationObj, HttpSession session)
	{
		if(employeeRegistrationObj.getEmpName() == "" || employeeRegistrationObj.getSoftHsmTokenLabel()=="" || employeeRegistrationObj.getWebAppPassword()==""|| employeeRegistrationObj.getWebAppUserName()== "" || employeeRegistrationObj.getSoftHsmUserPin()=="")
		{
			session.setAttribute("message","please enter every value above.");
			return "redirect:/create";
		}
		else
		{
			//Code for softHSM
			MultipartFile multipartFileObj = tokenAndKeyGenerationServiceObj.tokenCreationAndKeyGeneration(employeeRegistrationObj);
			
			if(multipartFileObj != null)
				registrationDataStorageServiceObj.createEmployee(employeeRegistrationObj,multipartFileObj);
			return "token.jsp";
		}
	}
	
  
}
