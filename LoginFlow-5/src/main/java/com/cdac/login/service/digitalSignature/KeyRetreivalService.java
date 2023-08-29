package com.cdac.login.service.digitalSignature;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;
import com.cdac.login.service.registerAndLoginService.FileRemovalService;

@Service
public class KeyRetreivalService {
	@Autowired
	private DigitalSignatureJpaLoginRepositoryDAO daoObj;
	@Autowired
	private FileRemovalService fileRemovalServiceObj;
	
	public List<byte[]> keyStoreAndPublicKeyFileRetreival(String username) {
		EmployeeRegistration objToBeSigned = null;
		Optional<EmployeeRegistration> obj = daoObj.getTheEmpWhoWillSign(username);
		if(obj.isPresent())
			objToBeSigned =  obj.get();
		byte[] byteKeyStoreFile = objToBeSigned.getKeyStoreFile();
		byte[] bytePublicKeyFile = objToBeSigned.getPublicKeyFile();
		
		/**ADDING KEY-STORE-FILE & PUBLIC-KEY-FILE TO LIST*/
		ArrayList<byte []> list= new ArrayList<byte []>();
		list.add(byteKeyStoreFile);
		list.add(bytePublicKeyFile);
		return list;
	}
	
	public PrivateKey privateKeyRetreiver(byte[] byteKeyStoreFile, String username, String signPass){
		PrivateKey privateKeyObj = null;
		try {
			/** EXTRACTING THE KEY-STORE FILE.*/
			String currentDirPath = System.getProperty("user.dir");
			String path = String.format(currentDirPath + "\\DynamicFileStorageRepo\\%sKeyStore.p12", username);	//Interpolation
			FileOutputStream fileOutputStreamObj = new FileOutputStream(path);
			fileOutputStreamObj.write(byteKeyStoreFile);
			fileOutputStreamObj.close();
			
			/** READING THE INPUT-STREAM FROM KEYSTORE.*/
			FileInputStream keyStoreFileInputStream = new FileInputStream(path);
			
			/** LOADING THE TOKEN/KEYSTORE */
			KeyStore keyStoreObj = KeyStore.getInstance("PKCS12");
			keyStoreObj.load(keyStoreFileInputStream, null);
			
			/** DEMO TO RETREIVE THE PRIVATE-KEY FROM KEYSTORE */
			privateKeyObj= (PrivateKey) keyStoreObj.getKey(String.format("%s", username), String.format("%s", signPass).toCharArray());	//Interpolation
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		
		/** REMOVING GENERATED KEY-STORE BECAUSE ALREADY SAVED IN DATABASE*/
		fileRemovalServiceObj.removeGeneratedKeyStoreFile(username);
		
		return privateKeyObj;
	}
}
