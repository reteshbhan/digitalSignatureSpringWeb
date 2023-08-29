# digitalSignatureSpringWeb
Hi!!
Thanks for visiting!

# LoginFlow-4(working):
This particular project implements the digital signature process by using Spring MVC framework. The entire project is built by using java libraries like:
1. Bouncy castle - for Cyptographic Operations like Signature and verification
2. PKCS12 java key store for securing the private keys and self-signed X.509 certificates(used to retrieve the private keys for signing purpose later in the code).
3. PDFBox Java library to mimic a physical signature.
4. Spring mailer to send the signed documents along with it's digital signature in its text form.

# LoginFlow-2:
I have one more implementation uploaded here in which SoftHSM is used instead of BouncyCastle. SoftHSM is a more secured software to save keys, certificates and do cryptographic operations like encryption, decryption, signing, etc. Unfortunately, due to the missing self signed certificates, this one does not work. The goal to upload this piece of code is to share valuable information about how to get around with SoftHSM using SunPKCS11 provider in java code.

# Front end: JSP, JSTL, EL(a little bit)

# Database: MySQL

The workflow of the webpage is something like this:
Users can register for this service. Every new user get an assymmetric key pair, which in turn is saved in Database. Public keys are written as objects into text files and private keys are saved in keystores(.p12 or .pkcs12 formats).
User1 uploads documents to get them digitally signed by User2. After reading the document, user2 will sign the document. This will trigger a mail to both sender and the signee. The signed document and a digital signature of the text file are sent in the mail as attachments. Later, User1 can verify the same document by uploading the signed file, digital signature text file and the signee's name.

I hope this project helps many people.

Always excited to receive feedback on reteshbhan@gmail.com.
   
Cheers!
