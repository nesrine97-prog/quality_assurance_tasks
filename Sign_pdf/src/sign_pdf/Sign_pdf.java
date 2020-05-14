package sign_pdf;

/**
 *
 * @author Aicha nesrine
 */

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
 
public class Sign_pdf  {
 
	/**
	 * Name of unsigned generated PDF document 
	 */
	static String fname  = "D:\\HelloWorld.pdf" ;
 
	/**
	 * Name of signed generated PDF document
	 */
	static String fnameS = "D:\\HelloWorld_sign.pdf" ;
 
	public static void main(String[] args) {
		try {
			Sign_pdf.buildPDF() ;
			Sign_pdf.signPdf() ;
		}
		catch(Exception e) { }
	}
 
	/**
	 * Creation of a simple PDF document "Hello World"
	 */
	public static void buildPDF() {
 
		// Document creation
		Document document = new Document();
 
		try {
			// Creation of the "writer" to the doc
			// directly to a file
			PdfWriter.getInstance(document,
					new FileOutputStream(fname));
			// Opening the document
			document.open();
 
			// Writing data
			document.add(new Paragraph("Hello World"));
 
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
 
		// Document closing
		document.close();
 
	}
	/**
	 * Document signature
	 */
	public static final boolean signPdf()
			throws IOException, DocumentException, Exception
	{       
                // You must specify here the path to your pkcs12 key
		String fileKey          = "C:\\MonRep\\MaClef.p12" ;
		// and here its "passPhrase"
		String fileKeyPassword  = "MonPassword" ;
 
		try {
			// Creation of a KeyStore
			KeyStore ks = KeyStore.getInstance("pkcs12");
			// Loading the p12 certificate
			ks.load(new FileInputStream(fileKey), fileKeyPassword.toCharArray());
			String alias = (String)ks.aliases().nextElement();
			// Recovering the private key
			PrivateKey key = (PrivateKey)ks.getKey(alias, fileKeyPassword.toCharArray());
			// and the certificate chain
			Certificate[] chain = ks.getCertificateChain(alias);
 
			// Reading the source document
			PdfReader pdfReader = new PdfReader((new File(fname)).getAbsolutePath());
			File outputFile = new File(fnameS);
			// Signature stamp creation
			PdfStamper pdfStamper;
			pdfStamper = PdfStamper.createSignature(pdfReader, null, '\0', outputFile);
			PdfSignatureAppearance sap = pdfStamper.getSignatureAppearance();
			sap.setCrypto(key, chain, null, PdfSignatureAppearance.SELF_SIGNED);
			sap.setReason("Test SignPDF berthou.mc");
			sap.setLocation("");
			// Stamp position on the page (here bottom left page 1)
			sap.setVisibleSignature(new Rectangle(10, 10, 50, 30), 1, "sign_rbl");
 
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
 
			return true;
		}
		catch (Exception key) {
			throw new Exception(key);
		}
	}
    
}
