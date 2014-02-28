package pe.cp.reportes.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ReportesConexion {
	
	private Connection conexion;
	private static String usuario;
	private static String password;
	
	private static String driver; 
	private static String beseDatos;
	
	private static ReportesConexion instancia;
	
	public static ReportesConexion getInstancia (){
		if(ReportesConexion.instancia==null){
		    ReportesConexion.instancia=new ReportesConexion();
		    usuario = getProperty("jdbc.username");
		    password = getProperty("jdbc.password");
		    driver = getProperty("jdbc.driverClassName");
		    beseDatos = getProperty("jdbc.url");
		 }
		return instancia;	
	}
	
	private static String getProperty(String nombre){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {	 
			input = new FileInputStream("classpath:WEB-INF/jdbc.properties");
	 
			// load a properties file
			prop.load(input);			
			return prop.getProperty(nombre);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
			return "";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void conectar()throws Exception {
	     //si la conecion es null nos conectamos
	        if(this.getConexion()!=null){
	            return;
	        }
	        else if(this.getConexion() == null){


	            try {
	                Class.forName(this.getDriver()) ; // obtine una istancia de la clase Diver
	// establece la conexion con el Diver jconector y este a su vez con la base de datos
	                this.setConexion(DriverManager.getConnection(this.getBeseDatos(), this.getUsuario(), this.getPassword()));
	              
	            } catch (SQLException ex) {
	              throw new Exception("ERROR AL CONECTARCE CON LA BASE DE DATOS");
	            } catch (ClassNotFoundException ex) {
	                 throw new Exception("Clase no encontrada");
	            }
	  }
		
	}
	
	/** desconecta de la base de datos */
    public void desconectar()throws Exception{
    if(this.getConexion()==null)
        this.setConexion(null);

    }

    public ReportesConexion() {
    }
    /*Metodos getter y setter*/
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getBeseDatos() {
        return beseDatos;
    }

    public void setBeseDatos(String beseDatos) {
        this.beseDatos = beseDatos;
    }

}
