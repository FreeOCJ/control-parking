package pe.cp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.cp.core.dao.ClienteDao;
import pe.cp.core.domain.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteDao cdao;
	
	@Override
	public int agregar(Cliente cliente) {
		return cdao.agregar(cliente);				
	}

	@Override
	public void actualizar(Cliente cliente) {
		cdao.actualizar(cliente);		
	}

	@Override
	public void eliminar(int idCliente) {
		cdao.eliminar(idCliente);
	}

	@Override
	public List<Cliente> buscar(String nombreComercial) {
		return cdao.buscar(nombreComercial);
	}

}
