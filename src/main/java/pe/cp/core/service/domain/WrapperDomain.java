package pe.cp.core.service.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import pe.cp.core.domain.Auditoria;
import pe.cp.core.domain.Cliente;
import pe.cp.core.domain.Incidencia;
import pe.cp.core.domain.Operacion;
import pe.cp.core.domain.OperacionDetalle;
import pe.cp.core.domain.OperacionPorTarifa;
import pe.cp.core.domain.Rol;
import pe.cp.core.domain.Tarifa;
import pe.cp.core.domain.TipoIncidencia;
import pe.cp.core.domain.UnidadOperativa;
import pe.cp.core.domain.Usuario;

public class WrapperDomain {
	
	public static UsuarioView ViewMapper(Usuario usuario){
		UsuarioView usuarioview = new UsuarioView();
		usuarioview.setId(usuario.getId());
		usuarioview.setNombres(usuario.getNombres());
		usuarioview.setApellidos(usuario.getApellidos());
		usuarioview.setCargo(usuario.getCargo());
		usuarioview.setRoles(usuario.getRoles());
		usuarioview.setEmail(usuario.getEmail());
		usuarioview.setLogin(usuario.getLogin());
		usuarioview.setPassword(usuario.getPassword());
		usuarioview.setNombreCompleto(usuario.getNombres() + " " + usuario.getApellidos());
		if (usuario.getCliente() != null)
			usuarioview.setCliente(usuario.getCliente());
		else
			usuarioview.setCliente(new Cliente());
		return usuarioview;
	}
	
	public static RolView ViewMapper(Rol rol){
		RolView rolView = new RolView();
		rolView.setId(rol.getId());
		rolView.setNombre(rol.getDescripcion());
		
		return rolView;
	}
	
	public static ClienteView ViewMapper(Cliente cliente){
		ClienteView clienteView = new ClienteView();
		clienteView.setId(cliente.getId());
		clienteView.setNombreComercial(cliente.getNombreComercial());
		clienteView.setRazonSocial(cliente.getRazonSocial());
		clienteView.setRuc(cliente.getRuc());
		
		return clienteView;
	}
	
	public static UnidadOperativaView ViewMapper(UnidadOperativa unidadOp){
		UnidadOperativaView unidadOpView = new UnidadOperativaView();
		unidadOpView.setDepartamento(unidadOp.getDepartamento());
		unidadOpView.setDireccion(unidadOp.getDireccion());
		unidadOpView.setDistrito(unidadOp.getDistrito());
		unidadOpView.setHoraApertura(unidadOp.getHoraInicio());
		unidadOpView.setHoraCierre(unidadOp.getHoraFin());
		unidadOpView.setNombre(unidadOp.getNombre());
		unidadOpView.setNroCajones(unidadOp.getNumeroCajones());
		unidadOpView.setProvincia(unidadOp.getProvincia());
		unidadOpView.setId(unidadOp.getId());
		
		return unidadOpView;
	}
	
	public static TarifaView ViewMapper(Tarifa tarifa) { 
		TarifaView tarifaView = new TarifaView();
		tarifaView.setId(tarifa.getId());
		tarifaView.setCategoria(tarifa.getCategoria());
		tarifaView.setMonto(tarifa.getMonto());
		
		return tarifaView;
	}
	
	public static OperacionView ViewMapper(Operacion operacion) {
		OperacionView operacionView = new OperacionView();
		operacionView.setAjuste(operacion.getAjuste());
		operacionView.setCantidadPernoctadosFin(operacion.getCantidadPernoctadosFin());
		operacionView.setCantidadPernoctadosInicio(operacion.getCantidadPernoctadosInicio());
		operacionView.setCantidadPersonas(operacion.getCantidadPersonas());
		operacionView.setCantidadRaudos(operacion.getCantidadRaudos());
		operacionView.setCantidadVehiculosEntrada(operacion.getCantidadVehiculosEntrada());
		operacionView.setCantidadVehiculosSalida(operacion.getCantidadVehiculosSalida());
		operacionView.setCreador(operacion.getCreador());
		operacionView.setEliminado(operacion.isEliminado());
		operacionView.setEstado(operacion.getEstado());
		operacionView.setFechaActualizacion(operacion.getFechaActualizacion());
		operacionView.setFechaCreacion(operacion.getFechaCreacion());
		operacionView.setFechaTransaccion(operacion.getFechaTransaccion());
		operacionView.setId(operacion.getId());
		operacionView.setIdUnidadOperativa(operacion.getIdUnidadOperativa());
		operacionView.setNumeroTicketFin(operacion.getNumeroTicketFin());
		operacionView.setNumeroTicketInicio(operacion.getNumeroTicketInicio());
		operacionView.setUltimoModificador(operacion.getUltimoModificador());
		operacionView.setNombreCliente(operacion.getAuxNombreCliente());
		operacionView.setNombreUnidadOperativa(operacion.getAuxNombreUnidadOperativa());
		operacionView.setOferta(operacion.getOferta());
		
		return operacionView;
	}
	
	public static OperacionDetalleView ViewMapper(OperacionDetalle detalle) {
		OperacionDetalleView detalleView = new OperacionDetalleView();
		detalleView.setCantidadIngreso(detalle.getCantidadIngresos());
		detalleView.setCantidadPersonas(detalle.getCantidadPersonas());
		detalleView.setCantidadSalida(detalle.getCantidadSalidas());
		detalleView.setIdOperacionDetalle(detalle.getIdOperacion());
		detalleView.setIdOperacionDetalle(detalle.getIdOpDetalle());
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String horaInicio = formatter.format(detalle.getHoraInicio()); 
		String horaFin = formatter.format(detalle.getHoraFin());

		String horario = String.format("%s - %s", horaInicio, horaFin);
		
		detalleView.setHorario(horario);
		
		return detalleView;
	}
	
	public static OperacionPorTarifaView ViewMapper(OperacionPorTarifa operacionPorTarifa) {
		OperacionPorTarifaView view = new OperacionPorTarifaView();
		view.setIdOpTarifa(operacionPorTarifa.getIdOperacionPorTarifa());
		view.setCantidad(operacionPorTarifa.getCantidadTickets());
		view.setDescripcion(String.format("%s - %s", operacionPorTarifa.getCategoria(), String.valueOf(operacionPorTarifa.getPrecioTarifa())));
		view.setMonto(operacionPorTarifa.getMonto());
        view.setPrecioTarifa(operacionPorTarifa.getPrecioTarifa());
		
		return view;
	}
	
	public static IncidenciaView ViewMapper(Incidencia incidencia) {
		IncidenciaView view = new IncidenciaView();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		view.setDetalle(incidencia.getDescripcion());
		view.setHora(sdf.format(incidencia.getFechaIncidencia()));
		view.setId(incidencia.getId());
		view.setTipo(incidencia.getTipoIncidencia().getDescripcion());
		view.setFechaIncidencia(incidencia.getFechaIncidencia());
		return view;
	}
	
	public static TipoIncidenciaView ViewMapper(TipoIncidencia tipo) {
		TipoIncidenciaView view = new TipoIncidenciaView();
		view.setId(tipo.getId());
		view.setTipo(tipo.getDescripcion());
		
		return view;
	}
	
	public static AuditoriaView ViewMapper(Auditoria audit) {
		AuditoriaView view = new AuditoriaView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy dd:mm");
		
		view.setDescripcion(audit.getEvento());
		view.setFechaHora(sdf.format(audit.getFechaCreacion()));
		view.setId(view.getId());
		view.setTipoEvento(audit.getTipoEvento());
		view.setUsuario(audit.getLoginUsuario());
		
		return view;
	}
}
