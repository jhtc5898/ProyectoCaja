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
	private String nombre;
	private String numeroCuenta;
	
	//Inicio de sesion
	public String iniciarSesion() {
		
		Cuenta cuentaUsuario = this.validarUsuario();
		
		if(cuentaUsuario != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", this.email);
			this.nombre = cuentaUsuario.getUsuario().getNombre()+" "+cuentaUsuario.getUsuario().getApellido();
			this.numeroCuenta=cuentaUsuario.getNumero_cuenta();
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
	
	//Si se encuentra un usuario
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
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	@Override
	public String toString() {
		return "LoginBean [gc=" + gc + ", email=" + email + ", contrasena=" + contrasena + ", user=" + user
				+ ", nombre=" + nombre + ", numeroCuenta=" + numeroCuenta + "]";
	}

	
}
