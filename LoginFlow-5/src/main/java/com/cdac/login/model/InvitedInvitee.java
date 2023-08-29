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
 
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity								//Annotation for Table in DB
@Table(name="invitedinvitee")
//@Scope("prototype")
public class InvitedInvitee implements Serializable{
	//static int idGenerator =1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id								//Annotation for Primary Key
	private Integer id;						//Notice the data type Integer & not int. Used in Repository package, while extending JpaRepository
	private String inviteeName;				//Same as "webAppUserName" from "EmployeeRegistration" class.  Also a FOREIGN KEY.
	private String invitedName;
	@Lob
	private byte[] digitallyUnsignedFile;
	private boolean isSigned ;
	private String timeOfUpload;
	
	/* @Lob Annotation: In JPA, the @Lob annotation can be applied to an attribute
	 * (usually a field) in an entity class to specify that the corresponding database
	 * column should be treated as a LOB. This annotation can be used with various
	 * data types, such as byte[] (for binary data) or String (for character data).*/
	@Lob
	private byte[] digitallySignedFile;
	private String fileName;
	
	
	//Parameterised Constructor
	public InvitedInvitee(Integer id, String inviteeName, String invitedName, byte[] digitallyUnsignedFile, boolean isSigned,
			byte[] digitallySignedFile, String fileName, String timeOfUpload) {
		this.id = id;		//Auto-increment in MySql
		this.inviteeName = inviteeName;							// Same as "webAppUserName" from table "EmployeeRegistration". Also a FOREIGN KEY.
		this.invitedName = invitedName;
		this.digitallyUnsignedFile = digitallyUnsignedFile;
		this.isSigned = isSigned;
		this.digitallySignedFile = digitallySignedFile;
		this.fileName = fileName;
		this.timeOfUpload = timeOfUpload;
	}
	
	//Default Constructor
	public InvitedInvitee() {
		
	}
	
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}

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


	public String getTimeOfUpload() {
		return timeOfUpload;
	}

	public void setTimeOfUpload(String timeOfUpload) {
		this.timeOfUpload = timeOfUpload;
	}

	//To string
	@Override
	public String toString() {
		return "InvitedInvitee [id=" + id + ", inviteeName=" + inviteeName + ", invitedName=" + invitedName
				+ ", isSigned=" + isSigned + "]";
	}
}
