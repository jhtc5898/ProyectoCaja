package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.UsuarioDAO;
import modelo.Usuario;

@Stateless
public class GestionUsuarios {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	public String guardarUsuario(Usuario usuario) {
		String resultado = "";
		if (usuarioDAO.read(usuario.getCedula()) == null) {
			usuarioDAO.insert(usuario);
		}else {
			resultado = null;
		}
		return resultado;
	}

	public void eliminarUsuario(String cedula) {
		if (usuarioDAO.read(cedula) != null) {
			usuarioDAO.remove(cedula);
		}
	}
	
	public void actualizarUsuario(Usuario usuario) {
		usuarioDAO.update(usuario);
	}

	public Usuario obtenerUsuarioAprobado(String numeroCedula) {
		return usuarioDAO.obtenerUsuarioAprobado(numeroCedula);
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
