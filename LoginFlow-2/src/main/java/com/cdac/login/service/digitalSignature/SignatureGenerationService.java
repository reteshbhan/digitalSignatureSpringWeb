package com.cdac.login.service.digitalSignature;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
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

import com.cdac.login.service.objectSerializationAndDeserialization.ObjectDeserializationService;


@Service
public class SignatureGenerationService  implements Serializable{
	@Autowired
	ObjectDeserializationService objectDeserializationServiceObj;
	private byte[] sign;
	KeyPair keyPairObj;
	
	public void digitalSignature(byte[] keyFileInBytes, byte[] unsignedFileInBytes) {
		keyPairObj = objectDeserializationServiceObj.deserialize(keyFileInBytes);
		try {
			Signature signatureObj = Signature.getInstance("SHA256WithRSA");
			signatureObj.initSign(keyPairObj.getPrivate());
			signatureObj.update(unsignedFileInBytes);
			sign = signatureObj.sign();
			System.out.println(sign);
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
	}

	public void digitalSignatureVerification(byte[] keyFileInBytes, byte[] unsignedFileInBytes) {
		try {
			Signature signatureVerificationObj = Signature.getInstance("SHA256WithRSA");
			signatureVerificationObj.initVerify(keyPairObj.getPublic());
			signatureVerificationObj.update(unsignedFileInBytes);
			boolean match = signatureVerificationObj.verify(sign);
			
			System.out.println("match final--> "+match);
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
	}
}
