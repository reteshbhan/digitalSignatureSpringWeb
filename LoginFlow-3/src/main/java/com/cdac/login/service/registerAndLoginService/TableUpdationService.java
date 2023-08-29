package com.cdac.login.service.registerAndLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;

import jakarta.transaction.Transactional;

@Service
public class TableUpdationService {
	@Autowired
	private DigitalSignatureJpaLoginRepositoryDAO daoObj;
	
	/* Error: jakarta.persistence.TransactionRequiredException: Executing an update/delete query.
	 * Solution: https://stackoverflow.com/questions/25821579/transactionrequiredexception-executing-an-update-delete-query
	 * 
	 * What is @Transactional and how is it related to @Modifying in Spring JPA?
	 * @Transactional is an annotation in the Spring Framework used to define the scope of a transaction for methods within
	 * a class. It ensures that a series of database operations either complete successfully or are rolled back if an error
	 * occurs, i.e. to maintain data consistency and integrity.
	 * @Modifying is another Spring annotation used in conjunction with @Query annotations to indicate that a method modifies
	 * the database, such as performing an update or delete operation. When used with @Transactional, it ensures that the
	 * modifying operation is part of the transaction, maintaining data integrity.
	 * In summary, @Transactional defines transaction boundaries, and when combined with @Modifying, it enables safe
	 * modification of the database within those transactions.*/
	@Transactional
	public void updateTable(Integer rowID) {
		daoObj.deleteTheRow(rowID);
	}
	
}
