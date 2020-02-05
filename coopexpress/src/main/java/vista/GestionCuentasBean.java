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
		
	@Inject
	private GestionUsuariosBean gub;
	
	private Cuenta cuenta = new Cuenta();
	private List<Cuenta> cuentas;
	private List<SelectItem> cuentasItem;
	private String mensaje;
	private String nroCuenta;
		
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
			setMensaje("Cuenta creada exitosamente");
			gub.init();
			pagina= "listar-cuenta";
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
			setMensaje("Su cuenta ha sido creada exitosamente, puede ingresar a Coopexpress en 'Iniciar Sesión'");
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
		return "listar-cuenta";
	}
	
	public String cuentaActualizar(Cuenta cuenta) {
		this.cuenta=cuenta;
		return "actualizar-cuenta";
	}
	
	public Cuenta cargarCuenta(String numeroCuenta) {
		return cuenta= gc.obtenerCuentaNumero(numeroCuenta);
	}
	
	public String eliminar(Cuenta cuenta) {
		gc.eliminar(cuenta);
		this.init();
		return null;
	}
	
	public String buscar() {
		cuenta = gc.obtenerCuentaNumero(nroCuenta);
		if(cuenta != null) {
			return "actualizar-cuenta";
		}else {
			FacesContext.getCurrentInstance().addMessage("buscarCuenta:txtCuenta", new FacesMessage("Cuenta no encontrada"));
			return null;
		}
		
	}
	
	public String eliminarUsuario(String numeroCuenta) {
		this.cuenta= cargarCuenta(numeroCuenta);
		login.cerrarSesion();
		gc.actualizarEliminado(this.cuenta);
		return "logout";
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
	
	public void vaciarMensaje() {
		setMensaje(" ");
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	
}
