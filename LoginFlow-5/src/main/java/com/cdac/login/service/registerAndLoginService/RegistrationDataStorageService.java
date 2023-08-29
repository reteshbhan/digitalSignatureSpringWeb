package com.cdac.login.service.registerAndLoginService;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.repositoryDAO.DigitalSignatureJpaRegisterRepository;

@Service
public class RegistrationDataStorageService
{
	@Autowired
	private DigitalSignatureJpaRegisterRepository rep;
	@Autowired
	private FileRemovalService fileRemovalServiceObj;
	
	public void createEmployee(EmployeeRegistration employeeRegistrationObj, List<MultipartFile> multipartFileList)
	{
		/** SAVING THE EMPLOYEE REGISTRATION OBJECT IN DATABASE*/
		try {
			employeeRegistrationObj.setKeyStoreFile(multipartFileList.get(0).getBytes());
			employeeRegistrationObj.setPublicKeyFile(multipartFileList.get(1).getBytes());
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
		rep.save(employeeRegistrationObj);
		
		/** REMOVING GENERATED PUBLIC-KEY FILE BECAUSE ALREADY SAVED IN DATABASE*/
		fileRemovalServiceObj.removeGeneratedSerializePublicKeyFile(employeeRegistrationObj.getWebAppUserName());
	}

}
