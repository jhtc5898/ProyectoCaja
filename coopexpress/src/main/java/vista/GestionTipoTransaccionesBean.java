package vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Tipo_Transaccion;
import negocio.GestionTipoTransaccion;

@ManagedBean
@SessionScoped
public class GestionTipoTransaccionesBean {

	@Inject
	private GestionTipoTransaccion gtt;
	
	private List<Tipo_Transaccion> tipoTransaccionList;
	
	private Tipo_Transaccion tipoTransaccion = new Tipo_Transaccion();
	
	@PostConstruct
	public void init() {
		tipoTransaccion = new Tipo_Transaccion();
	}

	
	public String guardarTipoTransaccion() {
		gtt.guardarTipoTransaccion(tipoTransaccion);
		tipoTransaccionList = gtt.getTipoTransacciones();
		init();
		return "lista-tipo-transaccion";
	}
	
	public String eliminarTipoTransaccion() {
		gtt.elimnarTipoTransaccion(tipoTransaccion.getCodigo_tipo_transaccion());
		return null;
	}
	
	public String actualizarTipoTransaccion() {
		gtt.actualizarTipoTransaccion(tipoTransaccion);
		return null;
	}

	public List<Tipo_Transaccion> getTipoTransaccionList() {
		return tipoTransaccionList;
	}

	public void setTipoTransaccionList(List<Tipo_Transaccion> tipoTransaccionList) {
		this.tipoTransaccionList = tipoTransaccionList;
	}

	public Tipo_Transaccion getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(Tipo_Transaccion tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
}
