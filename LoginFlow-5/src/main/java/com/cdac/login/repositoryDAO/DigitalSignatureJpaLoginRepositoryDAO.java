package com.cdac.login.repositoryDAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.model.InvitedInvitee;

public interface DigitalSignatureJpaLoginRepositoryDAO extends JpaRepository<InvitedInvitee,Integer> {

	@Query("from InvitedInvitee where invitedName=?1")
	List<InvitedInvitee> individualPendingSignatureList(String username);
	
	@Query("from InvitedInvitee where inviteeName=?1")
	List<InvitedInvitee> requestedSignatureList(String username);
	
	@Query("from EmployeeRegistration where webAppUserName=?1")
	Optional<EmployeeRegistration> findEmployeePresence(String invited);
		
	@Query("from EmployeeRegistration where webAppUserName=?1")
	Optional<EmployeeRegistration> getTheEmpWhoWillSign(String username);
	
	/* https://www.baeldung.com/spring-data-jpa-modifying-annotation
	 * The @Modifying annotation is used to enhance the @Query annotation so that we can execute not only
	 * SELECT queries, but also INSERT, UPDATE, DELETE, and even DDL queries.
	 * 
	 * https://www.baeldung.com/spring-data-jpa-modifying-annotation
	 * When we don't put the @Modifying annotation on the delete query, we get an InvalidDataAccessApiUsage
	 * exception. */
	@Modifying
	@Query("delete from InvitedInvitee where id=?1")
	void deleteTheRow(Integer rowID);
	
	@Query("select webAppPassword from EmployeeRegistration where webAppUserName=?1")
	Optional<String> getPasswordOfLoggingUser(String user);
	
	@Query("select isSigned from InvitedInvitee where id=?1")
	boolean isDocumentAlreadySigned(Integer rowID);
	
	@Query("select keystorePassword from EmployeeRegistration where webAppUserName=?1")
	Object keyStorePassword(String username);

	@Query("from InvitedInvitee where id=?1")
	Optional<InvitedInvitee> getUnsignedFileRowForDownload(Integer rowID);

	@Query("select publicKeyFile from EmployeeRegistration where webAppUserName=?1")
	byte[] getPublicKeyFile(String username);
	
	@Query("select email from EmployeeRegistration where webAppUserName=?1")
	String getEmail(String username);
}
