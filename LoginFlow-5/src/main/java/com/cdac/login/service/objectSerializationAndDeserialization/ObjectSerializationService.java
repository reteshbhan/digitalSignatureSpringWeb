package com.cdac.login.service.objectSerializationAndDeserialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

@Service
public class ObjectSerializationService {
	
	public File fileWrite(PublicKey publicKeyObj, String WebAppUserName) {
		String currentPathDir = System.getProperty("user.dir");
		String path = String.format(currentPathDir+"\\DynamicFileStorageRepo\\%sObjSerialize.txt", WebAppUserName);
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(publicKeyObj); 
			objectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
		
		return file;
	}
}
