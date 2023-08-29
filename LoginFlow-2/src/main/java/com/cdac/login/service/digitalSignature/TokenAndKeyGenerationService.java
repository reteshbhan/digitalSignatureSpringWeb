package com.cdac.login.service.digitalSignature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.service.objectSerializationAndDeserialization.ObjectSerializationService;


@Service
public class TokenAndKeyGenerationService implements Serializable{
	
	KeyStore ks;
	
	@Autowired
	ObjectSerializationService objSerialization;
	
	public MultipartFile tokenCreationAndKeyGeneration(EmployeeRegistration employeeRegistrationObj) {
		char[] tokenPin = "ytrewq".toCharArray();
		
		/*	SUN version 19
			SunRsaSign version 19
			SunEC version 19
			SunJSSE version 19
			SunJCE version 19
			SunJGSS version 19
			SunSASL version 19
			XMLDSig version 19
			SunPCSC version 19
			JdkLDAP version 19
			JdkSASL version 19
			SunMSCAPI version 19
			SunPKCS11 version 19*/
//		System.out.println("Providers:" + Security.getProviders());
//		for(Provider p : Security.getProviders())
//		{
//			System.out.println(p);
//		}
	
		try {
			//System.out.println("getProvider() --> " + Security.getProvider("SunPKCS11") );
			//System.out.println(Security.addProvider(Security.getProvider("SunPKCS11")));
			int id = 37;
			String label = "myLabel";
			
//			try {
//				String filePath = "C:\\SoftHSM2\\lib\\softhsm2-x64.dll";
//				FileWriter fw = new FileWriter("softHsm.conf");
//				fw.write("name = SoftHSM\n" + "library = " + filePath);
//				fw.write("\n slot = 1389596022\n" + "attributes(generate, *, *) = {\n");
//				fw.write("\t CKA_TOKEN = true\n}\n" + "attributes(generate, CKO_CERTIFICATE, *) = {\n");
//				fw.write("\t CKA_PRIVATE = false\n}\n" + "attributes(generate, CKO_PUBLIC_KEY, *) = {\n");
//				fw.write("\t CKA_PRIVATE = false\n}\n" + "attributes(generate, *, *) = {\n");
//				fw.write("\t CKA_ID = " + id +"\n}\n" + "attributes(generate, *, *) = {\n");
//				fw.write("\t CKA_LABEL = " + label +"\n}\n");
//				fw.close();
//			} catch (IOException e2) {
//				e2.printStackTrace();
//			}
			
			/** SETTING THE PROVIDER */
			/* For windows - softHsm.conf
			 * For Linux - softHsm.cofg
			 * https://docs.oracle.com/en/java/javase/11/security/pkcs11-reference-guide1.html#GUID-C4ABFACB-B2C9-4E71-A313-79F881488BB9	*/
			/*	The option can be specified zero or more times, the options are processed in the order specified in the configuration file as
			 * described below. The attributes option has the format:
					attributes(operation, keytype, keyalgorithm) = {
					  name1 = value1
					  [...]
					}
				Valid values for operation are:
				generate, for keys generated via a KeyPairGenerator or KeyGenerator
				import, for keys created via a KeyFactory or SecretKeyFactory. This also applies to Java software keys automatically converted
				to PKCS#11 key objects when they are passed to the initialization method of a cryptographic operation, for example Signature.initSign().
				*, for keys created using either a generate or a create operation.
				Valid values for keytype are CKO_PUBLIC_KEY, CKO_PRIVATE_KEY, and CKO_SECRET_KEY, for public, private, and secret keys, respectively,
				and * to match any type of key.
				Valid values for keyalgorithm are one of the CKK_xxx constants from the PKCS#11 specification, or * to match keys of any algorithm.
				The algorithms currently supported by the SunPKCS11 provider are CKK_RSA, CKK_DSA, CKK_DH, CKK_AES, CKK_DES, CKK_DES3, CKK_RC4, CKK_BLOWFISH,
				and CKK_GENERIC.
				https://docs.oracle.com/javase/8/docs/technotes/guides/security/p11guide.html#Keys
				*/
			String configFile = "C:\\Users\\Lenovo\\OneDrive\\CDAC\\Project\\ProjectSTS\\LoginFlow-2\\softHsm.conf";
			Provider p = Security.getProvider("SunPKCS11");
			p = p.configure(configFile);
			Security.addProvider(p);
			
			
			/** LOADING THE TOKEN/KEYSTORE */
			/* When accessing the PKCS#11 token as a keystore via the java.security.KeyStore class, you can supply the PIN in
			 * the password input parameter to the load method
			 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/p11guide.html#Login */
			ks = KeyStore.getInstance("PKCS11",p);
			/* To create an empty keystore using the above load method, pass null as the InputStream argument.
			 * https://docs.oracle.com/javase/8/docs/api/java/security/KeyStore.html#load-java.io.InputStream-char:A-  */
			ks.load(null, tokenPin);
			
			
			
			/** GENERATING KEY-PAIR */
			KeyPairGenerator keyPairGeneratorObj = KeyPairGenerator.getInstance("RSA",p);
			keyPairGeneratorObj.initialize(1024);
			KeyPair keyPairObj = keyPairGeneratorObj.generateKeyPair();
			System.out.println("Till key pair generation... = "+ keyPairObj.toString());
			
			
			/** WRITING THE KEY-PAIR OBJECT INTO TEMPORARY FILE */
			/* "File" class vs "MultipartFile" class in java :
			 * The "File" class in Java represents a file or directory in the file system. It provides methods to manipulate
			 * file properties, perform file operations, and navigate directories. The "MultipartFile" class is specific to
			 * web applications using frameworks like Spring. It handles incoming file uploads from client browsers,
			 * encapsulating the uploaded file's data, content type, and other attributes.*/
			/* Can we cast file to multipartfile in java?
			 * No, you cannot directly cast a File object to a MultipartFile object in Java. The reason is that they are
			 * different classes with different purposes and functionalities.
			 * Correct way to do is :
			 * 		ultipartFile multipartFile = new MockMultipartFile("file",file.getName(), "text/plain", IOUtils.toByteArray(input));
			 * 		https://syntaxfix.com/question/16897/converting-file-to-multipartfile*/
			//MultipartFile multipartFileObj = (MultipartFile) objSerialization.fileWrite(keyPairObj);
			File file = objSerialization.fileWrite(keyPairObj);
			
			
			/** "File" to "MultipartFile" CONVERSION AND SAVING IN DATABASE */
			FileInputStream fileInputStreamObj = new FileInputStream(file);
			MultipartFile multipartFileObj = new MockMultipartFile (file.getName(), file.getName(), "text/plain", fileInputStreamObj);
			
			
			/*	Exception in thread "main" java.lang.IllegalArgumentException: Private key must be accompanied by certificate chain
				at java.base/java.security.KeyStore.setKeyEntry(KeyStore.java:1163)
				at com.example.demo.softhsm.Itteration1.tokenCreationAndLogin(Itteration1.java:79)
				at com.example.demo.softhsm.Itteration1.main(Itteration1.java:120)
			*/
//			ks.setKeyEntry("myKey", kp.getPrivate(), tokenPin , null);
//			ks.store(null);
  				
				
				/* 	
				 System.out.println(kp.getPrivate());
				 System.out.println(kp.getPublic());
				 Output:	
				 	SunPKCS11-SoftHSM RSA private key, 1024 bitstoken object, not sensitive, unextractable)
					SunPKCS11-SoftHSM RSA public key, 1024 bitstoken object)
  					modulus: 129072732075987051261832943179068987473521670685819251415374835908981662825363246809911627220485565589373737738691134812466800727119509657114380779965741230579423973951997798390082911896852813390061025425097548915770355083495030537748292385161485504130941816022373557199510051378649600386271863123425839179809
  					public exponent: 65537
				
				System.out.println(kp.getPrivate().getEncoded());
				System.out.println(kp.getPublic().getEncoded().toString());
				Output:
					null
					[B@335eadca
				*/
			System.out.println(keyPairObj.getPrivate());
			System.out.println(keyPairObj.getPublic());
			System.out.println(keyPairObj.getPrivate().getEncoded());
			System.out.println(keyPairObj.getPublic().getEncoded().toString());
			
			return multipartFileObj;
			
		} catch (KeyStoreException e) {
			System.out.println("KeyStoreException" + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException" + e.getMessage());
			e.printStackTrace();
		} catch (CertificateException e) {
			System.out.println("CertificateException" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}

















