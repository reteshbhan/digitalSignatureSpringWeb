package com.cdac.login.service.digitalSignature;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.login.repositoryDAO.DigitalSignatureJpaLoginRepositoryDAO;

@Service
public class PdfBoxService {
	@Autowired
	private DigitalSignatureJpaLoginRepositoryDAO daoObj;
	
	/** LOADING THE PDF DOCUMENT*/
	/* https://www.javatpoint.com/pdfbox-load-existing-document*/
	public PDDocument loadPdf(byte[] unsignedFileInBytes) {
		PDDocument unsignedPdfDoc = null;
		try {
			unsignedPdfDoc = PDDocument.load(unsignedFileInBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unsignedPdfDoc;
	}

	public byte[] putSignMark(byte[] unsignedFileInBytes, Integer rowID, String username) {
		PDDocument unsignedPdfDocObj = loadPdf(unsignedFileInBytes);
		/** GETTING THE LAST PAGE*/
		PDPage lastPdfPage = unsignedPdfDocObj.getPage(unsignedPdfDocObj.getNumberOfPages()-1);
		
		/** GETTING TIME-STAMP*/
		long timestampMillis = System.currentTimeMillis();
        // Convert timestamp to LocalDateTime (Java 8 and later)
        Instant instant = Instant.ofEpochMilli(timestampMillis);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		/** PREPARING CONTENT STREAM - ADDING TEXT TO THE PDF DOCUMENT*/
		/* https://www.javatpoint.com/pdfbox-adding-text*/
		try {
			/* Parameters:
			 * 		document: The document the page is part of.
			 * 		sourcePage: The page to write the contents to.
			 * 		appendContent: Indicates whether content will be overwritten, appended or prepended.
			 * 		compress: Tell if the content stream should compress the page contents.*/
			//ABSOLUTE POSITIONING
//			PDPageContentStream pDPageContentStreamObj = new PDPageContentStream(unsignedPdfDocObj, lastPdfPage, AppendMode.OVERWRITE, false);
//			pDPageContentStreamObj.beginText();
//			pDPageContentStreamObj.newLineAtOffset(10, 50);
//			pDPageContentStreamObj.setFont(PDType1Font.TIMES_BOLD, 14);
//			pDPageContentStreamObj.showText("Signed by Bob Kumar Pandey");
//			pDPageContentStreamObj.endText();
//			pDPageContentStreamObj.beginText();
//			pDPageContentStreamObj.newLineAtOffset(10, 35);
//			pDPageContentStreamObj.showText(localDateTime.toString());
//			pDPageContentStreamObj.endText();
			
			//RELATIVE POSITIONING
			PDPageContentStream pDPageContentStreamObj = new PDPageContentStream(unsignedPdfDocObj, lastPdfPage, AppendMode.APPEND, false);
			pDPageContentStreamObj.beginText();
			pDPageContentStreamObj.newLineAtOffset(10, 55);
			pDPageContentStreamObj.setFont(PDType1Font.TIMES_BOLD, 14);
			pDPageContentStreamObj.showText("Signed by "+username);
			pDPageContentStreamObj.newLineAtOffset(0, -20);
			pDPageContentStreamObj.showText(localDateTime.toString());
			pDPageContentStreamObj.endText();
			
			/** ADDING RECTANGLES*/
			/* https://www.javatpoint.com/pdfbox-adding-rectangles*/
			pDPageContentStreamObj.addRect(0, 0, 200, 100);
			pDPageContentStreamObj.setStrokingColor(Color.RED);
			pDPageContentStreamObj.setLineWidth(1);
			pDPageContentStreamObj.stroke();
			
			pDPageContentStreamObj.close();
			
			//https://stackoverflow.com/questions/44843095/verifying-pdf-signature-in-java-using-bouncy-castle-and-pdfbox
			//https://lists.apache.org/thread/r1n3qw0rtx8x7f63ngs8p8hf1vyzd3cm
			//https://github.com/BrentDouglas/pdfbox/blob/master/examples/src/main/java/org/apache/pdfbox/examples/signature/CreateSignature.java
//			/** SIGN STAMP ON DOCUMENT*/
//			PDSignature pdSignatureObj = new PDSignature();
//			pdSignatureObj.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
//			pdSignatureObj.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
//			pdSignatureObj.setName("Bob Kumar Pandey");
//			pdSignatureObj.setLocation("WFH");
//			pdSignatureObj.setSignDate(Calendar.getInstance());
//			unsignedPdfDoc.addSignature(pdSignatureObj);
//			
            // Save the modified PDF document
			String currentDir = System.getProperty("user.dir");
			String path = String.format(currentDir +"\\DynamicFileStorageRepo\\%s", daoObj.getUnsignedFileRowForDownload(rowID).get().getFileName());

			/** SAVING & COSING THE DOCUMENT IS VERY NECESSARY.*/
			unsignedPdfDocObj.save(path);
			unsignedPdfDocObj.close();
			
			File file = new File(path);
			FileInputStream fileInputStreamObj = new FileInputStream(file);
			MultipartFile keyStoreMultipartFileObj = new MockMultipartFile (daoObj.getUnsignedFileRowForDownload(rowID).get().getFileName(), daoObj.getUnsignedFileRowForDownload(rowID).get().getFileName(), "application/pdf", fileInputStreamObj);
			
			return keyStoreMultipartFileObj.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
