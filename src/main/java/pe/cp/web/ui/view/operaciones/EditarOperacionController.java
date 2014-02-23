package pe.cp.web.ui.view.operaciones;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.service.OperacionService;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.OperacionView;
import pe.cp.core.service.messages.ObtenerOperacionRequest;
import pe.cp.core.service.messages.ObtenerOperacionResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;

public class EditarOperacionController implements IEditarOperacionHandler {

	private ApplicationContext ac;
	private IEditarOperacionView view;
	
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	@Autowired
	private OperacionService opService;
	
	public EditarOperacionController(IEditarOperacionView view) {
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		opService = ac.getBean(OperacionService.class);
		this.view = view;
	}
	
	@Override
	public void cargar() {
		ObtenerOperacionResponse response = 
				opService.obtener(new ObtenerOperacionRequest(view.getIdOperacion()));
		if (response.isResultadoEjecucion()) {
			cargarCabecera(response.getOperacionView());
		} else {
			Logger.getAnonymousLogger().log(Level.WARNING, "nada");
		}
	}

	private void cargarCabecera(OperacionView opView) {
		view.getLblCreador().setValue(opView.getCreador());
		view.getLblEstado().setValue(opView.getEstado());
		view.getFechaRegistro().setValue(opView.getFechaCreacion().toString());
		
		ObtenerUnidadOperativaResponse response = 
				unidadOpService.obtenerPorId(new ObtenerUnidadOperativaRequest(opView.getIdUnidadOperativa()));
		if (response.isResultadoEjecucion()) {
			view.getLblNombreUnidadOp().setValue(response.getUnidadOpView().getNombre());
		} else {
			//TODO
		}
	}
	
	@Override
	public void guardar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enviarAprobar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarIncidencia() {
		// TODO Auto-generated method stub

	}

	@Override
	public void editarIncidencia(int idIncidencia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarIncidencia(int idIncidencia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aprobar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarUsuario() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validarEdicion() {
		// TODO Auto-generated method stub
		return false;
	}

}
