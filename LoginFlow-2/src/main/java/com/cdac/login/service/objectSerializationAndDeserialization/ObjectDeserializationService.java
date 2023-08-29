package com.cdac.login.service.objectSerializationAndDeserialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.KeyPair;

import org.springframework.stereotype.Service;

@Service
public class ObjectDeserializationService implements Serializable{
	public KeyPair deserialize(byte[] keyFileInBytes) {
		KeyPair keyPairObj = null;
		try {
			/** CONVERTING THE "keyFileInBytes" INTO AN ACTUAL FILE.*/
			//File file = new File("C:\\Users\\Lenovo\\OneDrive\\CDAC\\Project\\ProjectSTS\\LoginFlow-2\\src\\main\\java\\com\\cdac\\login\\service\\objectSerializationAndDeserialization\\temp1.txt");
			String path = "C:\\Users\\Lenovo\\OneDrive\\CDAC\\Project\\ProjectSTS\\LoginFlow-2\\src\\main\\java\\com\\cdac\\login\\service\\objectSerializationAndDeserialization\\objDeser.txt";
			//file.createNewFile();
			FileOutputStream fileOutputStreamObj = new FileOutputStream(path);
			fileOutputStreamObj.write(keyFileInBytes);
			
			/** DESERIALIZING THE "KeyPair" OBJECT FROM THE FILE CREATED ABOVE.*/
			FileInputStream fileInputStreamObj = new FileInputStream(path);
			ObjectInputStream objectInputStreamObj = new ObjectInputStream(fileInputStreamObj);
			keyPairObj = (KeyPair)objectInputStreamObj.readObject();
			
			objectInputStreamObj.close();
			fileOutputStreamObj.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyPairObj;
	}
}
