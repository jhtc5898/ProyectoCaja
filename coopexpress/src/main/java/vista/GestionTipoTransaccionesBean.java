package vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Tipo_Transaccion;
import negocio.GestionTipoTransaccion;

@ManagedBean
@SessionScoped
public class GestionTipoTransaccionesBean {

	@Inject
	private GestionTipoTransaccion gtt;
	
	private Tipo_Transaccion tipoTransaccion = new Tipo_Transaccion();
	private List<Tipo_Transaccion> tipoTransaccionList;
	
		
	@PostConstruct
	public void init() {
		tipoTransaccion = new Tipo_Transaccion();
		tipoTransaccionList=gtt.getTipoTransacciones();
	}

	public String guardarTipoTransaccion() {
		gtt.guardarTipoTransaccion(tipoTransaccion);
		tipoTransaccionList = gtt.getTipoTransacciones();
		init();
		return "listar-tipo-transaccion";
	}
	
	public String eliminarTipoTransaccion(int codigo) {
		gtt.elimnarTipoTransaccion(codigo);
		return "listar-tipo-transaccion";
	}
	
	public String actualizarTipoTransaccion() {
		gtt.actualizarTipoTransaccion(tipoTransaccion);
		tipoTransaccionList = gtt.getTipoTransacciones();
		init();
		return "listar-tipo-transaccion";
	}

	public String actualizar(Tipo_Transaccion tipoTransaccion) {
		this.tipoTransaccion=tipoTransaccion;
		return "actualizar-tipo-transaccion";
	}
	
	/*Getters and Setters*/
	public List<Tipo_Transaccion> getTipoTransaccionList() {
		init();
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
