package com.cdac.login.service.objectSerializationAndDeserialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyPair;

import org.springframework.stereotype.Service;

@Service
public class ObjectSerializationService implements Serializable{
	public File fileCreation() {
		File file = new File("C:\\Users\\Lenovo\\OneDrive\\CDAC\\Project\\ProjectSTS\\LoginFlow-2\\src\\main\\java\\com\\cdac\\login\\service\\objectSerializationAndDeserialization\\objSer.txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
		return file;
	}
	
	public File fileWrite(KeyPair keyPairObj) {
		File file = fileCreation();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			/** ERROR LINE BELOW
			 *  java.io.NotSerializableException: Cannot serialize sensitive and unextractable keys	*/
			objectOutputStream.writeObject(keyPairObj);
			objectOutputStream.close();
			
			//CODE TO DELETE THE TEMPORARY FILE
			
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
