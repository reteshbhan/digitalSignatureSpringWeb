package com.cdac.login.service.registerAndLoginService;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.repositoryDAO.DigitalSignatureJpaRegisterRepository;

@Service
public class RegistrationDataStorageService  implements Serializable
{
	@Autowired
	private DigitalSignatureJpaRegisterRepository rep;
	
	public void createEmployee(EmployeeRegistration employeeRegistrationObj, MultipartFile multipartFileObj)
	{
		try {
			employeeRegistrationObj.setKeyPairFile(multipartFileObj.getBytes());
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
		rep.save(employeeRegistrationObj);
	}

}
