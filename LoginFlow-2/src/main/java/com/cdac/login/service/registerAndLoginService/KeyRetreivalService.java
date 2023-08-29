package com.cdac.login.service.registerAndLoginService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;

@Service
public class KeyRetreivalService {
	@Autowired
	DigitalSignatureJpaLoginRepositoryDAO daoObj;
	
	public byte[] keyObjectRetreival(String username) {
		EmployeeRegistration objToBeSigned = null;
		Optional<EmployeeRegistration> obj = daoObj.getTheEmpWhoWillSign(username);
		if(obj.isPresent())
			objToBeSigned =  obj.get();
		byte[] byteFileToBeSigned = objToBeSigned.getKeyPairFile();
		return byteFileToBeSigned;
	}
}
