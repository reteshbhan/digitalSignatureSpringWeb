package com.cdac.login.repositoryDAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.login.model.EmployeeRegistration;

public interface DigitalSignatureJpaRegisterRepository extends JpaRepository<EmployeeRegistration, Integer>
{

}
