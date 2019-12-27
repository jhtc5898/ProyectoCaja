package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.TipoTransaccionDAO;
import modelo.Tipo_Transaccion;
import modelo.Usuario;

@Stateless
public class GestionTipoTransaccion {

	@Inject
	private TipoTransaccionDAO tipoTransaccionDAO;
	
	public void guardarTipoTransaccion(Tipo_Transaccion tipoTransaccion) {
		if(tipoTransaccionDAO.read(tipoTransaccion.getCodigo_tipo_transaccion())==null) {
			tipoTransaccionDAO.insertarTipoTransaccion(tipoTransaccion);
		}else {
			tipoTransaccionDAO.actualizarTipoTransaccion(tipoTransaccion);
		}
	}
	
	public void actualizarTipoTransaccion(Tipo_Transaccion tipoTransaccion) {
		tipoTransaccionDAO.actualizarTipoTransaccion(tipoTransaccion);
	}
	
	public void elimnarTipoTransaccion(int codigo) {
		tipoTransaccionDAO.remove(codigo);
	}
	
	public List<Tipo_Transaccion> getTipoTransacciones(){
		return tipoTransaccionDAO.getTipoTransacciones();
	}
	
	public Tipo_Transaccion getTipoTransaccionNombre(String nombre){
		return tipoTransaccionDAO.getTipoTransaccionNombre(nombre);
	}
}
