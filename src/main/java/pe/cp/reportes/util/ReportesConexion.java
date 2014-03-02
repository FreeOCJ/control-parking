package pe.cp.reportes.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReportesConexion {
	
	private Connection conexion;
	static ApplicationContext ac;
	
	public ReportesConexion(){			
	}

	
	public void conectar()throws Exception
	{
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");		
		try {
			DataSource ds = (DataSource)ac.getBean("dataSource");
	    	conexion = ds.getConnection();	              
	    } catch (SQLException ex) {
	    	throw new Exception("ERROR AL CONECTARCE CON LA BASE DE DATOS");
	    }
	}
	
	/** desconecta de la base de datos */
    public void desconectar()throws Exception{
    if(this.getConexion()==null)
        this.setConexion(null);

    }

    /*Metodos getter y setter*/
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

   

}
