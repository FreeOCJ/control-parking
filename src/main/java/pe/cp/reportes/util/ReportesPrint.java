package pe.cp.reportes.util;

import java.io.FileNotFoundException; 
import java.io.InputStream; 
import java.io.Serializable; 
import java.sql.Connection; 
import java.util.Locale; 
import java.util.Map; 
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter; 
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint; 
import net.sf.jasperreports.engine.JasperReport; 
import net.sf.jasperreports.engine.export.JRPdfExporter; 
import net.sf.jasperreports.engine.export.JRPdfExporterParameter; 
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter; 
import net.sf.jasperreports.engine.export.JRXlsExporter; 
import net.sf.jasperreports.engine.export.JRXlsExporterParameter; 
import net.sf.jasperreports.engine.util.JRSaver; 
import net.sf.jasperreports.view.JasperViewer;  

public class ReportesPrint implements Serializable{ 

private JasperReport reporte; 
private JasperPrint print; 

//exportar reporte a axcel 
public void reporteaExcel(InputStream rutaJrxml,
		String rutaArchivoXLS,Map<String, Object> parametros,
		Connection conexion) throws JRException, FileNotFoundException{ 
	this.reporte=JasperCompileManager.compileReport(rutaJrxml); 
	//luego ponemos los parametros que necesitamos: 
	print = JasperFillManager.fillReport(this.reporte, parametros, conexion); 
	JRXlsExporter exportador = new JRXlsExporter();
	exportador.setParameter(JRExporterParameter.JASPER_PRINT,print);
	exportador.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,rutaArchivoXLS);
	exportador.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS,true);
	exportador.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, false); 
	exportador.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER,false); 
	exportador.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,true);
	exportador.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE ,true); 
	exportador.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,true);
	exportador.exportReport(); 
}

//metodo para generar el reporte en pdf si que se puedan 
//copiar las imagenes ni el texto 

public boolean reporteaPDF(String ruta, 
		InputStream dataSourceName, Map<String, Object> params,
		Connection conn) throws ClassNotFoundException, JRException { 
	this.reporte=JasperCompileManager.compileReport(dataSourceName); 
	this.print = JasperFillManager.fillReport(this.reporte, params, conn); 
	if(this.print.getPages().isEmpty()){ return false; } 
	//int permisos =PdfWriter.ALLOW_PRINTING; 
	//Esta clase es la encargada de exportar el archivo a pdf final 
	JRExporter jtrtf= new JRPdfExporter(); 
	//jtrtf.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
	//jtrtf.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE); 
	//jtrtf.setParameter(JRPdfExporterParameter.PERMISSIONS, permisos); 
	jtrtf.setParameter(JRExporterParameter.JASPER_PRINT, this.print);  
	//Gurdamos una copia en el computador Ejemplo c:/reportes.jrprint
	JRSaver.saveObject(this.print,ruta+".jrprint"); 
	//Gurdamos una copia en el computador Ejemplo c:/reportes.pdf 
	jtrtf.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, ruta+".pdf"); 
	//este metodo exporta a los diferentes formatos en este caso pdf 
	jtrtf.exportReport(); 
	//Metodo que se encarga de mostrar el reporte en la pantalla 
	JasperViewer.viewReport(this.print,false,Locale.getDefault()); 
	return true;

}

}
