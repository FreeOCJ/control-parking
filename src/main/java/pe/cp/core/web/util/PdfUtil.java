package pe.cp.core.web.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

public class PdfUtil {

	public void concatenarPDFs(String rutadocumento,String[] files){
		Document document = new Document();
		try {
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(rutadocumento));
			document.open();
			PdfReader reader;
	        int n;
	        for (int i = 0; i < files.length; i++) {
	            reader = new PdfReader(files[i]);
	            n = reader.getNumberOfPages();
	            for (int page = 0; page < n; ) {
	                copy.addPage(copy.getImportedPage(reader, ++page));
	            }
	            copy.freeReader(reader);
	            reader.close();
	        }
	        document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
