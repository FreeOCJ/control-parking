package pe.cp.web.ui.handler.impl;

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
import pe.cp.web.ui.NavegacionUtil;
import pe.cp.web.ui.handler.ITarifaHandler;
import pe.cp.web.ui.view.ITarifaView;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

public class TarifaController implements ITarifaHandler {
	ApplicationContext ac;
	@Autowired
	private UnidadOperativaService unidadOpService;
	
	private final String ERR_NOMBRE_RESERVADO = "No se pueden usar los nombres RAUDOS, PERNOCTADOS o REGULAR";
	private final String ERR_NOMBRE_VACIO = "Debe ingresar un nombre para la tarifa";
	private final String ERR_NO_TARIFAS = "Debe agregar al menos un monto";
	private final String ERR_FORMATO_NUMERO = "Sólo puede añadir números decimales mayores o iguales a 0.00";
	
	private ITarifaView view;
	
	public TarifaController(ITarifaView view){
		ac = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/context.xml");
		unidadOpService = ac.getBean(UnidadOperativaService.class);
		this.view = view;
	}
	
	@Override
	public void anadir() {
		Notification notification = new Notification(ERR_FORMATO_NUMERO,Type.WARNING_MESSAGE);
		
		try {
		   double monto = Double.parseDouble(view.getTxtMonto().getValue());
		   if (monto < 0.0) {
			   notification.setPosition(Position.TOP_CENTER);
			   notification.show(Page.getCurrent()); 
			   view.getTxtMonto().setValue("");
		   } else {
			   view.getListaTarifas().addItem(monto);
			   view.getTxtMonto().setValue("");   
		   }
		} catch (NumberFormatException e) {
		    notification.setPosition(Position.TOP_CENTER);
			notification.show(Page.getCurrent());
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
		Subject currentUser = SecurityUtils.getSubject();
		String mensaje = validar();
		
		if (mensaje == null) {
			
		   int idUsuario = (Integer) currentUser.getSession().getAttribute("id_usuario");
		   double[] montos = new double[view.getListaTarifas().getItemIds().size()];
		   
		   int i = 0;
		   for (Object monto : view.getListaTarifas().getItemIds()) {
			   montos[i] = Double.valueOf(monto.toString());
			   i++;
		   }
		 
		   AgregarTarifaRequest request = 
				   new AgregarTarifaRequest(view.getIdUnidadOperativa(), montos, view.getTxtNombre().getValue(), idUsuario);
		   Response response = unidadOpService.agregarTarifa(request);
		   
		   if (response.isResultadoEjecucion()) {
			   currentUser.getSession().setAttribute("mensaje",new Notification(response.getMensaje(),Type.HUMANIZED_MESSAGE));
			   irManteniemientoUnidadOp();
		   } else {
			   Notification notification = new Notification(response.getMensaje(),Type.WARNING_MESSAGE);
			   notification.setPosition(Position.TOP_CENTER);
			   notification.show(Page.getCurrent());
		   }
		} else {
			Notification notification = new Notification(mensaje,Type.WARNING_MESSAGE);
		    notification.setPosition(Position.TOP_CENTER);
			notification.show(Page.getCurrent());
		}
	}

	@Override
	public void cancelar() {
		NavegacionUtil.irEditarUnidadOperativa(view.getIdCliente(), view.getIdUnidadOperativa());
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
				
				String nombre = view.getTxtNombre().getValue();
				if (nombre.toUpperCase().equals(unidadOpService.getConstanteRaudos()) ||
					nombre.toUpperCase().equals(unidadOpService.getConstantePernoctados()) ||
					nombre.toUpperCase().equals(unidadOpService.getConstanteRegular()))
					
					view.getTxtNombre().setEnabled(false);
				
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
	public String validar() {
		
		String nombre = view.getTxtNombre().getValue();
		int cantidad = view.getListaTarifas().getItemIds().size();
		
		if (nombre == null || nombre.isEmpty()) return ERR_NOMBRE_VACIO;
		
		/* 
		 * if (nombre.toUpperCase().equals(unidadOpService.getConstanteRaudos()) ||
			nombre.toUpperCase().equals(unidadOpService.getConstantePernoctados()) ||
			nombre.toUpperCase().equals(unidadOpService.getConstanteRegular()))
			   return ERR_NOMBRE_RESERVADO;
		*/
		
		if (cantidad == 0) return ERR_NO_TARIFAS;
		
		return null;
	}
	
	private void irManteniemientoUnidadOp() {
		UI.getCurrent().getNavigator().navigateTo(
				   ControlParkingUI.UNIDADOPERATIVA + "/" + view.getIdCliente() + "/" + view.getIdUnidadOperativa());
	}
}
