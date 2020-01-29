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
	private List<Cuenta> busqueda;
	private int numeroCuenta;
	private int codigoTipoCuenta;
	private String eliminarNumero;
	private String numeroCuenta2;
	
	public String guardar() {
		gc.guardar(cuenta);
		//cuentas=gc.getCuentas();
		init();
		return "listar-cuentas";
	}
	
	@PostConstruct
	public void init() {
		cuenta = new Cuenta();
		cuenta.setUsuario(new Usuario());
		cuenta.setTipo_cuenta(new Tipo_Cuenta());
		cuentas=gc.getCuentas();
		
		eliminarNumero=String.valueOf(0);
		numeroCuenta2=String.valueOf(0);
	}
	
	public String actualizarCuenta() {
		gc.actualizar(cuenta);
		cuentas = gc.getCuentas();
		init();
		return "listar-cuentas";
	}

	public void buscar() {
		cuenta=gc.obtenerCuentaNumero(eliminarNumero);
		//this.eliminar();
		this.init();
	}
	
	public void buscarCuenta(){
		cuenta=gc.obtenerCuentaNumero(numeroCuenta2);
	}
	
	public String cuentaActualizar(Cuenta cuenta) {
		this.cuenta=cuenta;
		return "actualizar-cuenta";
	}
		
	public String eliminar(int codigo) {
//		int cod=Integer.parseInt(codigo);
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
	
//	oo
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

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public List<Cuenta> getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(List<Cuenta> busqueda) {
		this.busqueda = busqueda;
	}

	public String getEliminarNumero() {
		return eliminarNumero;
	}

	public void setEliminarNumero(String eliminarNumero) {
		this.eliminarNumero = eliminarNumero;
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

	public int getCodigoTipoCuenta() {
		return codigoTipoCuenta;
	}

	public void setCodigoTipoCuenta(int codigoTipoCuenta) {
		this.codigoTipoCuenta = codigoTipoCuenta;
	}

	public String getNumeroCuenta2() {
		return numeroCuenta2;
	}

	public void setNumeroCuenta2(String numeroCuenta2) {
		this.numeroCuenta2 = numeroCuenta2;
	}

	
	
}
