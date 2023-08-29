package com.cdac.login.repositoryDAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
