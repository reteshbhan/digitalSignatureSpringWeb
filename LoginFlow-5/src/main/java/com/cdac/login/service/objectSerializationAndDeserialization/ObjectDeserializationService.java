package com.cdac.login.service.objectSerializationAndDeserialization;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

// "webAppUserName" is same as "username".
@Service
public class ObjectDeserializationService{
	public PublicKey deserializePublicKeyFile(byte[] bytePublicKeyFile, String webAppUserName) {
		PublicKey publicKey = null;
		try {
			/** CONVERTING THE "byteKeyStoreFile" INTO AN ACTUAL FILE.*/
			String currentDirPath = System.getProperty("user.dir");
			String path = String.format(currentDirPath+"\\DynamicFileStorageRepo\\%sObjDeSerialize.txt", webAppUserName);
			FileOutputStream fileOutputStreamObj = new FileOutputStream(path);
			fileOutputStreamObj.write(bytePublicKeyFile);
			
			/** DESERIALIZING THE "KeyPair" OBJECT FROM THE FILE CREATED ABOVE.*/
			FileInputStream fileInputStreamObj = new FileInputStream(path);
			ObjectInputStream objectInputStreamObj = new ObjectInputStream(fileInputStreamObj);
			publicKey = (PublicKey)objectInputStreamObj.readObject();
			
			objectInputStreamObj.close();
			fileOutputStreamObj.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	public byte[] deserializeDigSigFile(byte[] digSigByteFile) {

		ByteArrayInputStream bais = new ByteArrayInputStream(digSigByteFile);
		byte[] sign = bais.readAllBytes();
		
		return sign;
	}
}
