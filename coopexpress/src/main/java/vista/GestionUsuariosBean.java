package vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Tipo_Transaccion;
import modelo.Usuario;
import negocio.GestionUsuarios;

@ManagedBean
@SessionScoped
public class GestionUsuariosBean {

	@Inject
	private GestionUsuarios gu;

	@Inject
	private MessagesBean mensaje = new MessagesBean();
	
	/* Bean Properties */
	private Usuario usuario = new Usuario();
	private String cedula;
	private List<Usuario> usuarios;
	
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarios = gu.getUsuarios();
		cedula = "";

	}

	/* Action Controller */
	public String guardarUsuario() {
		gu.guardarUsuario(usuario);
		init();
		return "registro-pendiente";
	}
		
	public String buscar() {
		usuario = gu.obtenerUsuarioCedula(cedula);
		if(usuario != null) {
			return "actualizar-usuario";
		}else {
			mensaje.setMensaje("El usuario no se encuentra registrado");
			return null;
		}
		
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
		return "actualizar-usuario.xhtml";
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
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

}