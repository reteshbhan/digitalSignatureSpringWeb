/*
 create table InvitedInvitee(
	id int AUTO_INCREMENT PRIMARY KEY,
    inviteeName varchar(50) NOT NULL,
    invitedName varchar(50) NOT NULL,
    digitallyUnsignedFile mediumblob,
    isSigned boolean DEFAULT FALSE,
    digitallySignedFile mediumblob DEFAULT NULL,
    FOREIGN KEY(inviteeName) REFERENCES EmployeeRegistration(webAppUserName)
);


*/

package com.cdac.login.model;
 
import java.io.File;
import java.io.Serializable;

import org.springframework.context.annotation.Scope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity								//Annotation for Table in DB
@Table(name="invitedinvitee")
//@Scope("prototype")
public class InvitedInvitee implements Serializable{
	//static int idGenerator =1;
	
	@Id								//Annotation for Primary Key
//	@Column(name="id")
	private Integer id;						//Notice the data type Integer & not int. Used in Repository package, while extending JpaRepository
//	@Column(name="inviteeName")
	private String inviteeName;				//Same as "webAppUserName" from "EmployeeRegistration" class.  Also a FOREIGN KEY.
//	@Column(name="invitedName")
	private String invitedName;
	@Lob
//	@Column(name="digitallyUnsignedFile")
	private byte[] digitallyUnsignedFile;
//	@Column(name="isSigned")
	private boolean isSigned ;
	
	/* @Lob Annotation: In JPA, the @Lob annotation can be applied to an attribute
	 * (usually a field) in an entity class to specify that the corresponding database
	 * column should be treated as a LOB. This annotation can be used with various
	 * data types, such as byte[] (for binary data) or String (for character data).*/
	@Lob
//	@Column(name="digitallySignedFile")
	private byte[] digitallySignedFile;
	private String fileName;
	
	
	//Parameterised Constructor
	public InvitedInvitee(Integer id, String inviteeName, String invitedName, byte[] digitallyUnsignedFile, boolean isSigned,
			byte[] digitallySignedFile, String fileName) {
		//super();
		this.id = id;		//Auto-increment in MySql
		this.inviteeName = inviteeName;							// Same as "webAppUserName" from table "EmployeeRegistration". Also a FOREIGN KEY.
		this.invitedName = invitedName;
		this.digitallyUnsignedFile = digitallyUnsignedFile;
		this.isSigned = isSigned;
		this.digitallySignedFile = digitallySignedFile;
		this.fileName = fileName;
		
		//idGenerator++;
	}
	
	//Default Constructor
	public InvitedInvitee() {
		
	}
	
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}
//
//	public static void setId(int id) {
//		this.id = id;
//	}
	
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getInviteeName() {
		return inviteeName;
	}

	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}

	public String getInvitedName() {
		return invitedName;
	}

	public void setInvitedName(String invitedName) {
		this.invitedName = invitedName;
	}

	public byte[] getDigitallyUnsignedFile() {
		return digitallyUnsignedFile;
	}

	public void setDigitallyUnsignedFile(byte[] digitallyUnsignedFile) {
		this.digitallyUnsignedFile = digitallyUnsignedFile;
	}

	public boolean isSigned() {
		return isSigned;
	}

	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}

	public byte[] getDigitallySignedFile() {
		return digitallySignedFile;
	}

	public void setDigitallySignedFile(byte[] digitallySignedFile) {
		this.digitallySignedFile = digitallySignedFile;
	}
	
	public String getFileName() {
		return fileName;
	}


	//To string
	@Override
	public String toString() {
		return "InvitedInvitee [id=" + id + ", inviteeName=" + inviteeName + ", invitedName=" + invitedName
				+ ", isSigned=" + isSigned + "]";
	}
}
