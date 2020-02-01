package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import datos.UsuarioDAO;
import modelo.Usuario;
import vista.MessagesBean;

@Stateless
public class GestionUsuarios {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private MessagesBean mensaje = new MessagesBean();

	public void guardarUsuario(Usuario usuario) {
		if (usuarioDAO.read(usuario.getCedula()) == null) {
			usuarioDAO.insert(usuario);
		} else {
			usuarioDAO.update(usuario);
		}
	}

	public void eliminarUsuario(String cedula) {
		if (usuarioDAO.read(cedula) != null) {
			usuarioDAO.remove(cedula);
		}
	}

	public void actualizarUsuario(Usuario usuario) {
		usuarioDAO.update(usuario);
	}

	public Usuario getUsuarioCedula(String cedula) {
		return usuarioDAO.read(cedula);
	}

	public List<Usuario> getUsuarios() {
		return usuarioDAO.getUsuario();
	}
	
	public Usuario obtenerUsuarioCedula(String cedula) {
		return usuarioDAO.obtenerUsuario(cedula);
	}
}
