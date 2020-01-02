package vista;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import modelo.Usuario;
import negocio.GestionUsuarios;

@ManagedBean
@SessionScoped
public class GestionUsuariosBean {

	@Inject
	private GestionUsuarios gu;

	private List<Usuario> usuarios;

	/* Bean Properties */
	private Usuario usuario = new Usuario();

	private String cedula;

	/* Action Controller */
	public String guardarUsuario() {
		gu.guardarUsuario(usuario);
		//usuarios = gu.getUsuarios();
		init();
		return "listarusuarios";
		// return null;
	}

	public String eliminarUsuario() {
		gu.eliminarUsuario(usuario.getCedula());
		//usuarios = gu.getUsuarios();
		init();
		return "listarusuarios";
		// return null;
	}
	
	public String eliminarUsuarioCedula(String cedula) {
		gu.eliminarUsuario(cedula);
		init();
		return "listarusuarios";
	}
	
	public String actualizarUsuarioObjeto(Usuario usuario) {
		this.usuario=usuario;
		return "actualizarusuario";
	}
	
	public String actualizarFinal() {
		gu.actualizarUsuario(this.usuario);
		this.init();
		return "listarusuarios";
	}
	
	

	public void buscar() {
		usuario = gu.obtenerUsuarioCedula(cedula);
	}

	public String actualizarUsuario() {
		gu.actualizarUsuario(usuario);
		init();
		return null;
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

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarios = gu.getUsuarios();
	}
}