package vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private String nombreUsuario;
	private String numeroCuenta;

	// Inicio de sesion
	public String iniciarSesion() {

		Cuenta cuenta = validarLoginCuenta();

		if (cuenta != null ) {
			// Si se encontro USUARIO
			if(cuenta.getTipo_cuenta().getCodigo_tipo_cuenta()==1) {
				System.out.println("Entro En Usuario");
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", this.email);
				session.setAttribute("rol", String.valueOf(cuenta.getTipo_cuenta().getCodigo_tipo_cuenta()));
				this.nombreUsuario = cuenta.getUsuario().getNombre() + " "+ cuenta.getUsuario().getApellido();
				this.numeroCuenta = cuenta.getNumero_cuenta();
				return "Usuario/home-Usuario";
			}
			//Si se encontro ADMIN
			else if(cuenta.getTipo_cuenta().getCodigo_tipo_cuenta()==2) {
				System.out.println("Entro En Admin");
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", this.email);
				session.setAttribute("rol", String.valueOf(cuenta.getTipo_cuenta().getCodigo_tipo_cuenta()));
				this.nombreUsuario = cuenta.getUsuario().getNombre() + " "+ cuenta.getUsuario().getApellido();
				this.numeroCuenta = cuenta.getNumero_cuenta();
				return "admin/solicitudes-usuario.xhtml";
			}
			//Si se encontro CAJERO
			else if(cuenta.getTipo_cuenta().getCodigo_tipo_cuenta()==3) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", this.email);
				session.setAttribute("rol", String.valueOf(cuenta.getTipo_cuenta().getCodigo_tipo_cuenta()));
				this.nombreUsuario = cuenta.getUsuario().getNombre() + " "+ cuenta.getUsuario().getApellido();
				this.numeroCuenta = cuenta.getNumero_cuenta();
				return "cajera/home-cajera";
			}
		}
		return null;
	}
	
	public Cuenta validarLoginCuenta() {
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		cuentas = this.gc.getCuentasLogin();
		for(Cuenta c: cuentas) {
			if(c.getCorreo_cuenta().equals(this.getEmail()) && c.getPswd_cuenta().equals(this.getContrasena())) {
				return c;
			}
		}
		return null;
	}

	//Metodo que elimina la sesion y redirige al login
	public void cerrarSesion() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombre) {
		this.nombreUsuario = nombre;
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

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	@Override
	public String toString() {
		return "LoginBean [gc=" + gc + ", email=" + email + ", contrasena=" + contrasena + ", nombreUsuario="
				+ nombreUsuario + ", numeroCuenta=" + numeroCuenta + "]";
	}

}