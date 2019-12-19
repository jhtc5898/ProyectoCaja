package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import datos.UsuarioDAO;
import modelo.Usuario;

@Stateless
public class GestionUsuarios {

	@Inject
	private UsuarioDAO usuarioDAO;

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

	public List<Usuario> getUsuarioCedula(String cedula) {
		return usuarioDAO.getUsuarioCedula(cedula);
	}

	public List<Usuario> getUsuarios() {
		return usuarioDAO.getUsuario();
	}
}
