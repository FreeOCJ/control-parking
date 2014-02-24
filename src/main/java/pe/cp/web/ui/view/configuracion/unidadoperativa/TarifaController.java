package pe.cp.web.ui.view.configuracion.unidadoperativa;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.cp.core.domain.Rol;
import pe.cp.core.service.UnidadOperativaService;
import pe.cp.core.service.domain.TarifaView;
import pe.cp.core.service.messages.AgregarTarifaRequest;
import pe.cp.core.service.messages.ObtenerTarifasRequest;
import pe.cp.core.service.messages.ObtenerTarifasResponse;
import pe.cp.core.service.messages.ObtenerUnidadOperativaRequest;
import pe.cp.core.service.messages.ObtenerUnidadOperativaResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.web.ui.ControlParkingUI;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

public class TarifaController implements ITarifaHandler {
	ApplicationContext ac;
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	private ITarifaView view;
	
	public TarifaController(ITarifaView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		this.view = view;
	}
	
	@Override
	public void anadir() {
		try {
		   double monto = Double.valueOf(view.getTxtMonto().getValue());
		   view.getListaTarifas().addItem(monto);
		   view.getTxtMonto().setValue("");
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void retirar() {
		try {
		    view.getListaTarifas().removeItem(view.getListaTarifas().getValue());	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardar() {
		if (validar()) {
		   double[] montos = new double[view.getListaTarifas().getItemIds().size()];
		   
		   int i = 0;
		   for (Object monto : view.getListaTarifas().getItemIds()) {
			   montos[i] = Double.valueOf(monto.toString());
			   i++;
		   }
		 
		   AgregarTarifaRequest request = 
				   new AgregarTarifaRequest(view.getIdUnidadOperativa(), montos, view.getTxtNombre().getValue());
		   Response response = unidadOpService.agregarTarifa(request);
		   Subject currentUser = SecurityUtils.getSubject();
		   
		   if (response.isResultadoEjecucion()) {
			   currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
			   irManteniemientoUnidadOp();
		   } else {
			   Notification notification = new Notification(response.getMensaje(),Type.WARNING_MESSAGE);
			   notification.setPosition(Position.TOP_CENTER);
			   notification.show(Page.getCurrent());
		   }
		}
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cargar() {
		ObtenerUnidadOperativaRequest requestUnidOp = new ObtenerUnidadOperativaRequest(view.getIdUnidadOperativa());
		ObtenerUnidadOperativaResponse responseUnidadOp = unidadOpService.obtenerPorId(requestUnidOp);
		
		if (responseUnidadOp.isResultadoEjecucion()) {
			view.getLabelUnidadOperativa().setValue(responseUnidadOp.getUnidadOpView().getNombre());
			
			if (view.getCategoriaTarifa().trim().isEmpty())
				view.getLabelTitulo().setValue("Nueva Tarifa");
			else {
				view.getLabelTitulo().setValue("Editar Tarifa");
				view.getTxtNombre().setValue(view.getCategoriaTarifa());
				
				ObtenerTarifasRequest requestTarifas = new ObtenerTarifasRequest(view.getIdUnidadOperativa(), view.getCategoriaTarifa());
				ObtenerTarifasResponse responseTarifas = unidadOpService.obtenerTarifas(requestTarifas);
				
				if (responseTarifas.isResultadoEjecucion()) {
					for (TarifaView tarifaView : responseTarifas.getTarifasView())
					   view.getListaTarifas().addItem(tarifaView.getMonto());	
				} else { 
					//TODO
				}
			}
		} else {
			//TODO
		}
	}

	@Override
	public void validarUsuario() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no autenticado, redireccionando a login");
			UI.getCurrent().getNavigator().navigateTo("");
		}else{
			if (!currentUser.hasRole(Rol.ADMINISTRADOR)){
				Logger.getAnonymousLogger().log(Level.WARNING, "Usuario no tiene el Rol adecuado");
				currentUser.getSession().setAttribute("mensaje",new Notification("Usuario no tiene el Rol adecuado",Type.ERROR_MESSAGE));
				UI.getCurrent().getNavigator().navigateTo(ControlParkingUI.OPERACIONES);
			}
		}
	}

	@Override
	public boolean validar() {
		// TODO Que pasa si entra y cambia el nombre al de otra ya existente. Bloquear los nombres reservados RAUDOS, etc
		if (view.getTxtNombre().getValue().isEmpty()) {
			//TODO Notificacion
			return false;
		}
		
		if (view.getListaTarifas().getItemIds().size() <= 0) {
			//TODO Notificacion
			return false;
		}
		
		return true;
	}
	
	private void irManteniemientoUnidadOp() {
		UI.getCurrent().getNavigator().navigateTo(
				   ControlParkingUI.UNIDADOPERATIVA + "/" + view.getIdCliente() + "/" + view.getIdUnidadOperativa());
	}
}
