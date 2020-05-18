/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_pdf;

/**
 *
 * @author Aicha nesrine
 */
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Calendar;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
 
public class Test_pdf  {
	/**
	 * Name of PDF
	 */
	static String dirname  = "D:\\" ;// "D:\\"
	static String fname  = "HelloWorld_sign" ;
 
	public static void main(String[] args) {
		try {
			Test_pdf.verifyPdf() ;
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
 
	public static final boolean verifyPdf()
                   throws IOException, DocumentException, Exception
	{
		KeyStore kall = PdfPKCS7.loadCacertsKeyStore();
 
		PdfReader reader = new PdfReader(dirname + fname +".pdf");
		AcroFields af = reader.getAcroFields();
 
		// Recherche de l'ensemble des signatures
		ArrayList names = af.getSignatureNames();
 
		// Pour chaqu'une des signatures
		for (int k = 0; k < names.size(); ++k) {
		   String name = (String)names.get(k);
  		   // Affichage du nom
		   System.out.println("Signature name: " + name);
		   System.out.println("Signature covers whole document: "
                                + af.signatureCoversWholeDocument(name));
  		   // Affichage sur les revision - version
		   System.out.println("Document revision: " + af.getRevision(name) + " of "
                                + af.getTotalRevisions());
		   // Debut de l'extraction de la "revision"
		   FileOutputStream out = new FileOutputStream("d:\\revision_"
                                + af.getRevision(name) + ".pdf");
		   byte bb[] = new byte[8192];
		   InputStream ip = af.extractRevision(name);
		   int n = 0;
		   while ((n = ip.read(bb)) > 0) out.write(bb, 0, n);
		   out.close();
		   ip.close();
		   // Fin extraction revision
 
		   PdfPKCS7 pk = af.verifySignature(name);
		   Calendar cal = pk.getSignDate();
		   Certificate pkc[] = pk.getCertificates();
		   // Information sur le certificat, le signataire
		   System.out.println("Subject: "
                                + PdfPKCS7.getSubjectFields(pk.getSigningCertificate()));
		   // Le document à t'il ete modifié ?
		   System.out.println("Document modified: " + !pk.verify());
 
		   // Le certificat est il valide ? Attention on recherche la chaine de certificats
		   Object fails[] = PdfPKCS7.verifyCertificates(pkc, kall, null, cal);
		   if (fails == null)
		       System.out.println("Certificates verified against the KeyStore");
		   else
		       System.out.println("Certificate failed: " + fails[1]);
		}
		return true ;
	}
}
