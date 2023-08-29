package com.cdac.login.service.digitalSignature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
 * Token Generation and Login:
 * 1. Login to the KeyStore(SunPKCS11)
 * 		Load the KeyStore	load()
 * 		Access the keystore
 * 		*/

import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;
import com.cdac.login.service.emailService.EmailService;
import com.cdac.login.service.objectSerializationAndDeserialization.ObjectDeserializationService;
import com.cdac.login.service.registerAndLoginService.FileRemovalService;
import com.cdac.login.service.registerAndLoginService.TableUpdationService;


@Service
public class SignatureGenerationService{
	@Autowired
	private ObjectDeserializationService objectDeserializationServiceObj;
	private PublicKey publicKeyObj;
	@Autowired
	private KeyRetreivalService keyRetreivalServiceObj;
	@Autowired
	private TableUpdationService tableUpdationServiceObj;
	@Autowired
	private FileRemovalService fileRemovalServiceObj;
	@Autowired
	private PdfBoxService pdfBoxServiceObj;
	@Autowired
	private EmailService emailServiceObj;
	@Autowired
	private DigitalSignatureJpaLoginRepositoryDAO daoObj;
	
	/* Required things to sign a document:
	 * 1. Private key
	 * 2. Unsigned file in byte[]*/
	public void digitalSignature(byte[] byteKeyStoreFile, byte[] unsignedFileInBytes, String username, Integer rowID, String signPass) {
		byte[] sign = null;
		/** STAMPING THE DOCUMENT*/
		/**	CODE TO GENERATE A FILE FORMAT OF PDF USING BYTE[] FOR PDFBOX TO LOAD*/
		//String path = String.format("/DynamicFileStorageRepo/%s.pdf", daoObj.getUnsignedFileRowForDownload(rowID).get().getFileName());
//		pdfBoxServiceObj.putSignMark(unsignedFileInBytes, path, sign);
		byte[] stampedFile = pdfBoxServiceObj.putSignMark(unsignedFileInBytes,rowID,username);
		
		
		/** SIGNING PROCESS	*/
		PrivateKey privateKeyObj = keyRetreivalServiceObj.privateKeyRetreiver(byteKeyStoreFile, username, signPass);		//Copy Key-store generated is deleted in below function call
		try {
			Signature signatureObj = Signature.getInstance("SHA256WithRSA");
			signatureObj.initSign(privateKeyObj);
			signatureObj.update(stampedFile);
			sign = signatureObj.sign();
			
			
			
		} catch (InvalidKeyException e) {
			System.out.println("InvalidKeyException" + e.getMessage());
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("SignatureException" + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException" + e.getMessage());
			e.printStackTrace();
		}
		
		/** CODE TO DELETE THE COPY-KEYSTORE FILE GENERATED IN ABOVE FUNCTION CALL (.privateKeyRetreiver(......)) */
		fileRemovalServiceObj.removeGeneratedKeyStoreFile(username);
		
		/** CODE TO SEND MAIL*/
		String from = "akeyakb@gmail.com";
		String to[] = {daoObj.getEmail(username), daoObj.getEmail(daoObj.findById(rowID).get().getInviteeName())};
		String body = "This is an automated mail. Please don't reply back.";
		String subject = "Signed files by "+username + " - " + daoObj.getUnsignedFileRowForDownload(rowID).get().getFileName();
		
		/** CREATING FILE[]*/
		File[] fileArrayAttachments= creatingFileArrayToSend(rowID, sign);
		
		emailServiceObj.sendMimeEmail(from, to , body, subject, fileArrayAttachments);
		
		/** CODE TO CHANGE THE STATUS/DELETE IN TABLE*/
		tableUpdationServiceObj.updateTable(rowID);
	}

	/** VERIFICATION ALGORITHM
	 * @param username 
	 * @param bs */
	/* Required things to sign a document:
	 * 1. Public key
	 * 2. Unsigned file in byte[]
	 * 3. Digital Signature		*/
	public int digitalSignatureVerification(byte[] bytePublicKeyFile, byte[] signedFileInBytes, String username, byte[] digSigByteFile) {
		publicKeyObj = objectDeserializationServiceObj.deserializePublicKeyFile(bytePublicKeyFile, username);
		byte[] sign =objectDeserializationServiceObj.deserializeDigSigFile(digSigByteFile);
		boolean match = false;
		int flag=0;
		try {
			Signature signatureVerificationObj = Signature.getInstance("SHA256WithRSA");
			signatureVerificationObj.initVerify(publicKeyObj);
			signatureVerificationObj.update(signedFileInBytes);
			match = signatureVerificationObj.verify(sign);
			
		} catch (InvalidKeyException e) {
			System.out.println("InvalidKeyException" + e.getMessage());
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("SignatureException" + e.getMessage());
			flag=1;
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException" + e.getMessage());
			e.printStackTrace();
		}
		
		
		/** CODE TO DELETE THE COPY-DESERIALIZATION FILE GENERATED IN ABOVE FUNCTION CALL (.deserialize(....)) */
		fileRemovalServiceObj.removeGeneratedDeSerializePublicKeyFile(username);	// "webAppUserName" is same as "username".
		
		if(flag == 1)
			return 3;
		else if(match)
			return 1;
		else
			return 2;
	}
	
	private File[] creatingFileArrayToSend(Integer rowID, byte[] sign) {
		
		//SIGNED FILE
		String currentDir = System.getProperty("user.dir");
		String signedFilePath = currentDir + String.format("\\DynamicFileStorageRepo\\%s", daoObj.getUnsignedFileRowForDownload(rowID).get().getFileName());
		File signedFile = new File(signedFilePath);
		
		//PUBLIC KEY FILE
//		String publicKeyFilePath = currentDir + "\\DynamicFileStorageRepo\\publicKeyFile.txt";
//		byte[] publicKeyByteFile = daoObj.getPublicKeyFile(username);
//		File publicKeyFile = new File(publicKeyFilePath);
//		try {
//			FileOutputStream fileOutputStreamObj = new FileOutputStream(publicKeyFile);
//			fileOutputStreamObj.write(publicKeyByteFile);
//			fileOutputStreamObj.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//DIGITAL SIGNATURE FILE
		String digSigFilePath = currentDir + "\\DynamicFileStorageRepo\\digSigFile.txt";
		File digSigFile = new File(digSigFilePath);
		try {
			FileOutputStream fileOutputStreamObj = new FileOutputStream(digSigFile);
			fileOutputStreamObj.write(sign);
			fileOutputStreamObj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File[] fileArray = {signedFile, digSigFile};
		
		return fileArray;
	}
}
