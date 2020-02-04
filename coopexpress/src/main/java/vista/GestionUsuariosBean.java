package vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import modelo.Tipo_Transaccion;
import modelo.Usuario;
import negocio.GestionUsuarios;

@ManagedBean
@SessionScoped
public class GestionUsuariosBean {

	@Inject
	private GestionUsuarios gu;
		
	/* Bean Properties */
	private Usuario usuario = new Usuario();
	private String cedula;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Usuario> usuariosPendientes = new ArrayList<Usuario>();
	private String mensaje;
	private String mensaje1;
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarios = gu.getUsuarios();
		usuariosPendientes = gu.getUsuariosPendientes();
		cedula = " ";
	}

	//Registro del usuario con el mensaje de confirmacion
	public String guardarUsuario() {
		String pagina = "";
		if(gu.guardarUsuario(usuario)!= null) {
			init();
			setMensaje("Su solicitud ha sido ingresada correctamente. Deberá consultar si fue o no aprobado en 'Consultar Aprobacion' en el menú lateral");
		}else {
			FacesContext.getCurrentInstance().addMessage("register:txtCedula", new FacesMessage("La cédula ingresada ya se encuentra registrada"));
			pagina = null;
		}
		return pagina;
	}
		
	public String buscar() {
		usuario = gu.obtenerUsuarioCedula(cedula);
		if(usuario != null) {
			return "actualizar-usuario";
		}else {
			return null;
		}
		
	}
	
	public String consultarAprobacion() {
		usuario = gu.obtenerUsuarioAprobado(cedula);
		if(usuario != null) {
			setMensaje1("Usuario:  Aprobado, acercarse a crear su cuenta en la cooperativa"); 
		}else {
			setMensaje1("Usuario:  Denegado, para mas información acercarse a consultar a Coopexpress");
		}
		return null;
	}
	
	public String actualizarUsuarioUsuario() {
		gu.actualizarUsuario(usuario);
		usuarios = gu.getUsuarios();
		init();
		return "perfil";
	}
	
	public String actualizarUsuarioAdmin() {
		gu.actualizarUsuario(usuario);
		usuarios = gu.getUsuarios();
		init();
		return "listar-usuario";
	}

	public String actualizar(Usuario usuario) {
		this.usuario = usuario;
		return "actualizar-usuario";
	}
	
	public String cargarUsuarioRevision(Usuario usuario) {
		this.usuario = usuario;
		return "revision-usuario";
	}
	
	public String aprobarUsuario(Usuario usuario) {
		this.usuario = usuario;
		return "crear-cuenta";
	}
	
	public void vaciarMensaje() {
		setMensaje(" ");
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Usuario> getUsuariosPendientes() {
		return usuariosPendientes;
	}

	public void setUsuariosPendientes(List<Usuario> usuariosPendientes) {
		this.usuariosPendientes = usuariosPendientes;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCedula() {
		return cedula;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje1() {
		return mensaje1;
	}

	public void setMensaje1(String mensaje1) {
		this.mensaje1 = mensaje1;
	}

}