package com.cdac.login.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*

# Initialize a slot 
hashi@kakashi:~$ softhsm2-util --init-token --slot 0 --label Token1
=== SO PIN (4-255 characters) ===
Please enter SO PIN: **********
Please reenter SO PIN: **********
=== User PIN (4-255 characters) ===
Please enter user PIN: **********
Please reenter user PIN: **********
The token has been initialized and is reassigned to slot 525803377

//SO - Security Officer


create table EmployeeRegistration(
	empName varchar(50) NOT NULL,
    -- empId varchar(15) PRIMARY KEY,
    webAppUserName varchar(50) NOT NULL PRIMARY KEY,
    webAppPassword varchar(50) NOT NULL,
    softHsmSlot varchar(5),
    reassignedSlot varchar(5),
    softHsmTokenLabel varchar(20),
    softHsmUserPin int,
    publicKey varchar(2000)
);

*/
@Entity
@Table(name="employeeregistration")
//@Scope("prototype")
public class EmployeeRegistration implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empName;
	//String empId;
	@Id
	private String webAppUserName;		// Username to register to the webApp. This will the empID. Also a PRIMARY KEY.
	private String webAppPassword;		// Password to register to the webApp
//	private int softHsmSlot;			// Available slots e.g. slot 0, slot 1
//	private int reassignedSlot;			// ... reassigned to slot 525803377
//	private String softHsmTokenLabel;	// Remember "--label Token1" ?
	private String keystorePassword;			// Four or five digits.
	private byte[] publicKeyFile;			// HashCode
	private byte[] keyStoreFile;
	private String email;
	
	
	//Constructor
//	public EmployeeRegistration(String empName, String webAppUserName, String webAppPassword,
//			int softHsmSlot, int reassignedSlot, String softHsmTokenLabel, int softHsmUserPin, String publicKey) {
//		super();
//		this.empName = empName;
//		//this.empId = empId;
//		this.webAppUserName = webAppUserName;			//Same as empID. Also a PRIMARY KEY.
//		this.webAppPassword = webAppPassword;
//		this.softHsmSlot = softHsmSlot;
//		this.reassignedSlot = reassignedSlot;
//		this.softHsmTokenLabel = softHsmTokenLabel;
//		this.softHsmUserPin = softHsmUserPin;
//		this.publicKey = publicKey;
//	}

	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	//Getters and Setters
	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


//	public String getEmpId() {
//		return empId;
//	}
//
//
//	public void setEmpId(String empId) {
//		this.empId = empId;
//	}


	public String getWebAppUserName() {
		return webAppUserName;
	}


	public void setWebAppUserName(String webAppUserName) {
		this.webAppUserName = webAppUserName;
	}


	public String getWebAppPassword() {
		return webAppPassword;
	}


	public void setWebAppPassword(String webAppPassword) {
		this.webAppPassword = webAppPassword;
	}


//	public int getSoftHsmSlot() {
//		return softHsmSlot;
//	}
//
//
//	public void setSoftHsmSlot(int softHsmSlot) {
//		this.softHsmSlot = softHsmSlot;
//	}
//
//
//	public int getReassignedSlot() {
//		return reassignedSlot;
//	}
//
//
//	public void setReassignedSlot(int reassignedSlot) {
//		this.reassignedSlot = reassignedSlot;
//	}
//
//
//	public String getSoftHsmTokenLabel() {
//		return softHsmTokenLabel;
//	}
//
//
//	public void setSoftHsmTokenLabel(String softHsmTokenLabel) {
//		this.softHsmTokenLabel = softHsmTokenLabel;
//	}


	public String getKeystorePassword() {
		return keystorePassword;
	}


	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}


	public byte[] getPublicKeyFile() {
		return publicKeyFile;
	}


	public void setPublicKeyFile(byte[] publicKeyFile) {
		this.publicKeyFile = publicKeyFile;
	}
	
	public byte[] getKeyStoreFile() {
		return keyStoreFile;
	}


	public void setKeyStoreFile(byte[] keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	
	//To String
	@Override
	public String toString() {
		return "EmployeeRegistration [empName=" + empName + ", webAppUserName=" + webAppUserName
				+ ", webAppPassword=" + webAppPassword + ", softHsmSlot=" + ", reassignedSlot="
				+ ", softHsmTokenLabel=" + ", softHsmUserPin=" + keystorePassword + "]";
	}
}
