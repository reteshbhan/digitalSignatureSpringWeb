package com.cdac.login.service.digitalSignature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcContentSignerBuilder;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
//import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.model.EmployeeRegistration;
import com.cdac.login.service.objectSerializationAndDeserialization.ObjectSerializationService;


@Service
public class KeyStoreAndKeyAndCertificateGenerationService{
	
	private KeyPair keyPairObj;	
	@Autowired
	private ObjectSerializationService objSerializationServiceObj;
	
	public List<MultipartFile> keyStoreKeyCertificateGeneration(EmployeeRegistration employeeRegistrationObj) {
		String currentDirPath = System.getProperty("user.dir");
//		if(Files.exists(Path.of("DynamicFileStorageRepo"))) {
//            try {
//				Files.delete(Path.of("DynamicFileStorageRepo"));
//				File dir = new File("DynamicFileStorageRepo");
//				FileUtils.cleanDirectory(dir);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//        }
        
        /** LOADING THE TOKEN/KEYSTORE */
        KeyStore keyStoreObj = null;
		try {
			keyStoreObj = KeyStore.getInstance("PKCS12");
			keyStoreObj.load(null, null);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        /** GENERATING KEY-PAIR */
        KeyPairGenerator keyPairGeneratorObj;
        PublicKey publicKeyObj = null;
        PrivateKey privateKeyObj = null;
		try {
			keyPairGeneratorObj = KeyPairGenerator.getInstance("RSA");
			keyPairGeneratorObj.initialize(2048);
			keyPairObj = keyPairGeneratorObj.generateKeyPair();
			publicKeyObj = keyPairObj.getPublic();
			privateKeyObj = keyPairObj.getPrivate();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try {
			/**GENERATING A X509-SELFSIGNED CERTIFICATE*/
	        X509Certificate x509Certificate;
			x509Certificate = generateRootCert(keyPairObj);
			keyStoreObj.setCertificateEntry("myRootCertificate", x509Certificate);
	        Certificate[] certificateChain = {x509Certificate};
	        
	        
	        /**SAVING PRIVATE KEY USING SETKEYENTRY() TO RETRIEVE IT LATER */
	        keyStoreObj.setKeyEntry(String.format("%s", employeeRegistrationObj.getWebAppUserName()), privateKeyObj, String.format("%s", employeeRegistrationObj.getKeystorePassword()).toCharArray(), certificateChain);
	        keyStoreObj.store(new FileOutputStream(String.format(currentDirPath+"\\DynamicFileStorageRepo\\%sKeyStore.p12", employeeRegistrationObj.getWebAppUserName())), String.format("%s", employeeRegistrationObj.getKeystorePassword()).toCharArray());
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
//		Enumeration<String> enums;
//		try {
//			enums = keyStoreObj.aliases();
//			System.out.println("aliases:");
//			enums.asIterator().forEachRemaining(System.out::println);
//		} catch (KeyStoreException e) {
//			e.printStackTrace();
//		}
		
		/** CONVERTING THE KEYSTORE IN "FILE" FORMAT AND THEN CONVERTING TO MULTIPARTFILE*/
		String keyStorePath = String.format(currentDirPath+"\\DynamicFileStorageRepo\\%sKeyStore.p12", employeeRegistrationObj.getWebAppUserName());
		File keyStoreFile = new File(keyStorePath);
		
		
		/** KEY-STORE-FILE to "MultipartFile" CONVERSION */ 
		FileInputStream keyStoreFileInputStreamObj;
		MultipartFile keyStoreMultipartFileObj = null;
		try {
			keyStoreFileInputStreamObj = new FileInputStream(keyStoreFile);
			keyStoreMultipartFileObj = new MockMultipartFile (keyStoreFile.getName(), keyStoreFile.getName(), "text/plain", keyStoreFileInputStreamObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
        /** DEMO TO RETREIVE THE PRIVATE-KEY FROM KEYSTORE */
        /*Key key;
		try {
			key = keyStoreObj.getKey(String.format("%s", employeeRegistrationObj.getWebAppUserName()), "password".toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}*/
        
        
		/** DEMO TO RETREIVE THE CERTIFICATE FROM KEYSTORE */
        /*Certificate certificate;
		try {
			certificate = keyStoreObj.getCertificate("myRootCertificate");
			//System.out.println("certificate --> "+certificate.toString());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}*/
        
			
			
		/** WRITING THE PUBLIC-KEY OBJECT INTO TEMPORARY FILE */
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
		File publicKeyFile = objSerializationServiceObj.fileWrite(publicKeyObj, employeeRegistrationObj.getWebAppUserName());
		
		
		/** PUBLIC-KEY-FILE to "MultipartFile" CONVERSION */
		FileInputStream publicKeyFileInputStreamObj;
		MultipartFile publicKeyMultipartFileObj = null;
		try {
			publicKeyFileInputStreamObj = new FileInputStream(publicKeyFile);
			publicKeyMultipartFileObj = new MockMultipartFile (publicKeyFile.getName(), publicKeyFile.getName(), "text/plain", publicKeyFileInputStreamObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**ADDING KEY-STORE-FILE & PUBLIC-KEY-FILE TO LIST*/
		ArrayList<MultipartFile> list= new ArrayList<MultipartFile>();
		list.add(keyStoreMultipartFileObj);
		list.add(publicKeyMultipartFileObj);
		
		return list;
	} 
	
	
	/** SELF-SIGNED CERTIFICATE GENERATION*/
	/*the code generates a self-signed root X.509 certificate with a subject and issuer set to "CN=Root Certificate".
	 * This certificate is used as a root of trust in a Public Key Infrastructure (PKI) and can be used for various
	 * security purposes, such as signing other certificates or establishing trust in digital signatures. The
	 * certificate contains information about its issuer, subject, validity period, and the public key of the entity
	 * it represents.		*/
	public X509Certificate generateRootCert(KeyPair pair) throws IOException, OperatorCreationException, CertificateException {
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.DAY_OF_YEAR, 365);

//        final GeneralNames subjectAltNames = new GeneralNames(new GeneralName(GeneralName.iPAddress, "127.0.0.1"));
//        certificateBuilder.addExtension(org.bouncycastle.asn1.x509.Extension.subjectAlternativeName, false, subjectAltNames);

        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption);
        AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier(algorithmIdentifier.getAlgorithm(), DERNull.INSTANCE);
        final AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);
        final BcContentSignerBuilder signerBuilder = new BcRSAContentSignerBuilder(sigAlgId, digAlgId);
        final AsymmetricKeyParameter keyp = PrivateKeyFactory.createKey(pair.getPrivate().getEncoded());
        final ContentSigner signer = signerBuilder.build(keyp);

        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(
                new X500Name("CN=Root Certificate"),
                BigInteger.ONE,
                new Date(),
                expiry.getTime(),
                Locale.ENGLISH,
                new X500Name("CN=Root Certificate"),
                SubjectPublicKeyInfo.getInstance(pair.getPublic().getEncoded())
        );
        final X509CertificateHolder x509CertificateHolder = x509v3CertificateBuilder.build(signer);
        final X509Certificate certificate = new JcaX509CertificateConverter()
                .getCertificate(x509CertificateHolder);
        return certificate;
    }
}


















