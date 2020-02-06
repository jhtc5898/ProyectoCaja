package vista;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import modelo.Cuenta;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class RecuperarBean {
	
	@Inject
	private GestionCuentas gc;
	
	private Cuenta cuenta =  new Cuenta();
	private String correo;
	private String clave1;
	private String clave2;
	
	@PostConstruct
	public void init() {
		cuenta = new Cuenta();
		correo = " ";
	}
	
	public String recuperarContraseña() {
		cuenta = gc.getCuentaCorreo(correo);
		String pagina = "";
		if(cuenta != null) {
			pagina = "cambiar-clave";
		}else {
			FacesContext.getCurrentInstance().addMessage("recuperar:txtCorreo", new FacesMessage("Correo no encontrado"));
		}
		return pagina;
	}
	
	public void cambiarContraseña() {
		if(clave1.equals(clave2)) {
			cuenta.setPswd_cuenta(clave1);
			gc.actualizar(cuenta);
			FacesContext.getCurrentInstance().addMessage("cambio:txtContrasena2", new FacesMessage("La contraseña ha sido actualizada"));
		}else {
			FacesContext.getCurrentInstance().addMessage("cambio:txtContrasena2", new FacesMessage("Las contraseñas no coinciden"));
		}
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave1() {
		return clave1;
	}

	public void setClave1(String clave1) {
		this.clave1 = clave1;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}
	
}
