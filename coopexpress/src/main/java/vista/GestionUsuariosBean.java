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

	/* Action Controller */
	public String guardarUsuario() {
		gu.guardarUsuario(usuario);
		usuarios=gu.getUsuarios();
		init();
		return "listarusuarios";
		//return null;
	}

	public String eliminarUsuario() {
		gu.eliminarUsuario(usuario.getCedula());
		usuarios=gu.getUsuarios();
		init();
		return "listarusuarios";
		//return null;
	}

	public String actualizarUsuario() {
		gu.actualizarUsuario(usuario);
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
	@PostConstruct
	public void init() {
		usuario=new Usuario();
	}
}
