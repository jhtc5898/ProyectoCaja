package vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import utils.SessionUtils;

import modelo.Cuenta;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	@Inject
	private GestionCuentas gc;
	
	private String email;
	private String contrasena;
	private String user;
	private String nameUser;
	private String cuenta;
	
	public String iniciarSesion() {
		
		Cuenta cuenta = this.validarUsuario();
		
		if(cuenta != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", this.email);
			this.nameUser=cuenta.getNumero_cuenta();
			this.cuenta=cuenta.getNumero_cuenta();
			return "deposito.xhtml";
		}
		
		return null;
	}
	
	public String cerrarSesion() {
		HttpSession session = SessionUtils.getSession();
		System.out.println(session.toString());
		session.invalidate();
		return "login.xhtml";
	}
	
	public Cuenta validarUsuario() {		
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		cuentas = this.gc.getCuentas();		
		for(Cuenta c: cuentas) {
			if(c.getCorreo_cuenta().equals(this.getEmail()) && c.getPswd_cuenta().equals(this.getContrasena())) {
				return c;
			}
		}
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public String toString() {
		return "LoginBean [nameUser=" + nameUser + "]";
	}
	
}
