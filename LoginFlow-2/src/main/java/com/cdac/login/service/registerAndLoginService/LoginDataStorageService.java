package com.cdac.login.service.registerAndLoginService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.model.InvitedInvitee;
import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;

/*`@Service` is a Spring Framework annotation used in Java to indicate that a class is a
 * service component. It's typically applied to classes that provide business logic or
 * other services within a Spring-based application. By marking a class with `@Service`,
 * Spring's component scanning mechanism identifies it and makes it available for dependency
 * injection. This enables you to manage the lifecycle of the service, handle transactions,
 * and leverage other Spring features easily. Essentially, it's a way to define a service
 * layer component in Spring, promoting separation of concerns and enabling better organization
 * of your application's architecture.*/
@Service 
public class LoginDataStorageService implements Serializable{
	@Autowired
	DigitalSignatureJpaLoginRepositoryDAO daoObj;
	
	public void uploadFile(String invited, MultipartFile file, String invitee) {
		
		InvitedInvitee ii = null;
		/* Below line normally looks like this:
		 * InvitedInvitee ii = new InvitedInvitee(invitee, invited, file.getBytes(), false, null);
		 * I just added a try/catch block.*/
		try {
			ii = new InvitedInvitee(0,invitee, invited, file.getBytes(), false, null, file.getOriginalFilename());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		daoObj.save(ii);	//JPA function call
	}
	
	
	/*In Spring Boot, the term "Model" typically refers to the data structure used to represent
	 * the information being processed within your application, often as part of the
	 * Model-View-Controller (MVC) architecture. Here's a concise explanation:

		A "Model" in Spring Boot:
		- Represents the data or state of the application.
		- Can be a Java class with fields representing the data.
		- Used to transfer data between components, such as controllers and views.
		- Often annotated with `@Entity` for JPA entities or simply used as DTOs (Data Transfer Objects).
		- Part of the MVC pattern, helping separate concerns in your application.
		
		The "Model" encapsulates the information your application works with, and it's a key component
		in building robust and maintainable Spring Boot applications.
		*/
	public List<InvitedInvitee> getIndividualFilesList(String username) {
		List<InvitedInvitee> ls = daoObj.individualPendingSignatureList(username);
		return ls;
		
	}


	public List<InvitedInvitee> getRequestedFilesList(String username) {
		List<InvitedInvitee> ls = daoObj.requestedSignatureList(username);
		return ls;
	}


	public boolean confirmEmployeePresence(String invited) {
		System.out.println("Inside fss confirmEmployeePresence -> "+ invited);
		Optional<EmployeeRegistration> empList = daoObj.findEmployeePresence(invited);
		
		if(empList.isEmpty())
			return true;
		return false;
	}
	
	public byte[] getUnsignedFile(Integer rowID) {
		Optional<InvitedInvitee> obj = daoObj.findById(rowID);
		InvitedInvitee selectedFileRow = null;
		if(obj.isPresent())
			selectedFileRow = obj.get();
		return selectedFileRow.getDigitallyUnsignedFile();
	}
}
