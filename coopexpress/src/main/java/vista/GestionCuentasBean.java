package vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import modelo.Cuenta;
import modelo.Tipo_Cuenta;
import modelo.Usuario;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class GestionCuentasBean implements Serializable{

	private static final long serialVersionUID = -8788194274849479867L;

	@Inject
	private GestionCuentas gc;
	
	private Cuenta cuenta = new Cuenta();
	private List<Cuenta> cuentas;
	private List<SelectItem> cuentasItem;
	
	public String guardar() {
		gc.guardar(cuenta);
		init();
		return "listar-cuentas";
	}
	
	@PostConstruct
	public void init() {
		cuenta = new Cuenta();
		cuenta.setUsuario(new Usuario());
		cuenta.setTipo_cuenta(new Tipo_Cuenta());
		cuentas=gc.getCuentas();
	}
	
	public String actualizarCuentaUsuario() {
		gc.actualizar(cuenta);
		init();
		return "perfil";
	}
	
	public String actualizarCuentaAdmin() {
		gc.actualizar(cuenta);
		init();
		return "listar-cuentas";
	}
	
	public String cuentaActualizar(Cuenta cuenta) {
		this.cuenta=cuenta;
		return "actualizar-cuenta";
	}
	
	public Cuenta cargarCuenta(String numeroCuenta) {
		return cuenta= gc.obtenerCuentaNumero(numeroCuenta);
	}
	
	public String eliminar(int codigo) {
		gc.eliminar(codigo);
		this.init();
		return null;
	}
	
	public List<SelectItem> getItems(){
		this.cuentasItem= new ArrayList<SelectItem>();
		List<Tipo_Cuenta> tc = gc.comboBox();
		cuentasItem.clear();
		
		for(Tipo_Cuenta tipocuentas: tc) {
			SelectItem tipoItem = new SelectItem(tipocuentas.getCodigo_tipo_cuenta(),tipocuentas.getNombre_tipo_cuenta());
			this.cuentasItem.add(tipoItem);
		}
		return cuentasItem;
	}
	
	public String nombreTipoCuenta() {
		return gc.nombreTipoCuenta(this.cuenta.getTipo_cuenta().getCodigo_tipo_cuenta());
	}
	
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public List<Cuenta> getCuentas() {
		this.init();
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public List<SelectItem> getCuentasItem() {
		
		this.cuentasItem= new ArrayList<SelectItem>();
		
		List<Tipo_Cuenta> tc = gc.comboBox();
		//cuentasItem.clear();
		
		for(Tipo_Cuenta tipocuentas: tc) {
			SelectItem tipoItem = new SelectItem(tipocuentas.getCodigo_tipo_cuenta(),tipocuentas.getNombre_tipo_cuenta());
			this.cuentasItem.add(tipoItem);
		}
		
		return cuentasItem;
	}

	public void setCuentasItem(List<SelectItem> cuentasItem) {
		this.cuentasItem = cuentasItem;
	}
	
}
