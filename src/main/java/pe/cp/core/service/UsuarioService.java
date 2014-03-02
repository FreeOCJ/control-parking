package pe.cp.core.service;

import pe.cp.core.domain.Usuario;
import pe.cp.core.service.messages.ActualizarUsuarioRequest;
import pe.cp.core.service.messages.ActualizarUsuarioResponse;
import pe.cp.core.service.messages.BuscarUsuarioRequest;
import pe.cp.core.service.messages.BuscarUsuarioResponse;
import pe.cp.core.service.messages.EliminarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioRequest;
import pe.cp.core.service.messages.InsertarUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteRequest;
import pe.cp.core.service.messages.ObtenerUsuarioPorClienteResponse;
import pe.cp.core.service.messages.ObtenerUsuarioPorUnidadOpRequest;
import pe.cp.core.service.messages.ObtenerUsuarioPorUnidadOpResponse;
import pe.cp.core.service.messages.ObtenerUsuarioRequest;
import pe.cp.core.service.messages.ObtenerUsuarioResponse;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaRequest;
import pe.cp.core.service.messages.ObtenerUsuariosSistemaResponse;
import pe.cp.core.service.messages.Response;
import pe.cp.core.service.messages.ValidarDatosUsuarioRequest;
import pe.cp.core.service.messages.ValidarDatosUsuarioResponse;

public interface UsuarioService {
	ActualizarUsuarioResponse actualizar(ActualizarUsuarioRequest request);
	InsertarUsuarioResponse agregar(InsertarUsuarioRequest request);
	void eliminar(int id);
	BuscarUsuarioResponse buscarOr(BuscarUsuarioRequest request);
	ObtenerUsuarioResponse buscar(ObtenerUsuarioRequest request);
	Usuario buscarPorLogin(String login);
	ObtenerUsuariosSistemaResponse obtenerUsuariosSistema(ObtenerUsuariosSistemaRequest request);
	ObtenerUsuarioPorClienteResponse obtenerUsuariosPorCliente(ObtenerUsuarioPorClienteRequest request);
	ObtenerUsuarioPorUnidadOpResponse obtenerUsuariosPorUnidadOP(ObtenerUsuarioPorUnidadOpRequest request);
	ValidarDatosUsuarioResponse validarDatosUsuario(ValidarDatosUsuarioRequest request);
	Response eliminarUsuario(EliminarUsuarioRequest request);
	boolean validarNuevoUsuario(Usuario usuario);
	boolean validarUsuarioModificado(Usuario usuario, Usuario usuarioModificado);
	String generarContrasenaTemporal();
}
