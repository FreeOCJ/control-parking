package pe.cp.core.web.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.vaadin.server.StreamResource.StreamSource;

@SuppressWarnings("serial")
public class PdfSource implements StreamSource {

	private String rutaArchivo;
	
	public PdfSource(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
	@Override
	public InputStream getStream() {
		try {
			return new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(rutaArchivo + ".pdf")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}					
	}	
}
