package com.cdac.login.service.registerAndLoginService;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class FileRemovalService {
	private String currentPathDir = System.getProperty("user.dir");
	/** REMOVING GENERATED KEY-STORE BECAUSE ALREADY SAVED IN DATABASE*/
	public void removeGeneratedKeyStoreFile(String webAppUserName) {
		String path = String.format(currentPathDir+"\\DynamicFileStorageRepo\\%sKeyStore.p12", webAppUserName);
//		File keyStoreFileToBeDeletedObj = new File(path);
//		keyStoreFileToBeDeletedObj.delete();
		
		
		try {
            File keystoreFile = new File(path);
            System.out.println(keystoreFile.exists());
            if (keystoreFile.exists()) {
                if (keystoreFile.delete()) {
                    System.out.println("Keystore deleted successfully.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}

	/** REMOVING GENERATED PUBLIC-KEY FILE BECAUSE ALREADY SAVED IN DATABASE*/
	public void removeGeneratedDeSerializePublicKeyFile(String username) {
		String path = String.format(currentPathDir+"\\DynamicFileStorageRepo\\%sObjDeSerialize.txt", username);
		File DeSerializePublicKeyFileToBeDeletedObj = new File(path);
		DeSerializePublicKeyFileToBeDeletedObj.delete();
	}

	public void removeGeneratedSerializePublicKeyFile(String username) {
		String path = String.format(currentPathDir+"\\DynamicFileStorageRepo\\%sObjSerialize.txt", username);
		File serializePublicKeyFileToBeDeletedObj = new File(path);
		serializePublicKeyFileToBeDeletedObj.delete();
	}
}
