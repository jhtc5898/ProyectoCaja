package vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import modelo.Cuenta;
import modelo.Tipo_Cuenta;
import modelo.Usuario;
import negocio.GestionCuentas;
import negocio.GestionUsuarios;

@ManagedBean
@SessionScoped
public class GestionCuentasBean implements Serializable{

	private static final long serialVersionUID = -8788194274849479867L;

	@Inject
	private GestionCuentas gc;
	
	@Inject
	private GestionUsuarios gu;
	
	@Inject
	private LoginBean login;
		
	private Cuenta cuenta = new Cuenta();
	private List<Cuenta> cuentas;
	private List<SelectItem> cuentasItem;
	private String mensaje;
		
	@PostConstruct
	public void init() {
		cuenta = new Cuenta();
		cuenta.setUsuario(new Usuario());
		cuenta.setTipo_cuenta(new Tipo_Cuenta());
		cuentas=gc.getCuentas();
	}
	
	public String guardar() {
		gc.guardar(cuenta);
		init();
		return "listar-cuentas";
	}
	
	public String guardarCuenta(String cedula) {
		String pagina = "";
		this.cuenta.setUsuario(gu.getUsuarioCedula(cedula));
		if(gc.guardar(cuenta)!= null) {
			init();
			setMensaje("Su cuenta ha sido registrada exitosamente, puede ingresar a Coopexpress en 'Iniciar Sesión'");
		}else {
			FacesContext.getCurrentInstance().addMessage("register:txtCedula", new FacesMessage("El correo ya esta en uso"));
			pagina = null;
		}
		return pagina;
	}
	
	public String guardarCuentaUsuario(String cedula) {
		String pagina = "";
		this.cuenta.setUsuario(gu.getUsuarioCedula(cedula));
		if(gc.guardarUsuario(cuenta)!= null) {
			init();
			setMensaje("Su cuenta ha sido registrada exitosamente, puede ingresar a Coopexpress en 'Iniciar Sesión'");
		}else {
			FacesContext.getCurrentInstance().addMessage("register:txtCorreo", new FacesMessage("El correo ya esta en uso"));
			pagina = null;
		}
		return pagina;
	}
	
	
	public String actualizarCuentaUsuario() {
		gc.actualizar(cuenta);
		login.cerrarSesion();
		return "logout";
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
