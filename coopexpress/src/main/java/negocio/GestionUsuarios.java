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

	public Usuario obtenerUsuarios(String numeroCedula) {
		return usuarioDAO.obtenerUsuario(numeroCedula);
	}
	
	public Usuario getUsuarioCedula(String cedula) {
		return usuarioDAO.read(cedula);
	}

	public void aprobarUsuario(String cedula) {
		usuarioDAO.aprobarUsuario(cedula);
	}
	
	public void denegarUsuario(String cedula) {
		usuarioDAO.denegarUsuario(cedula);
	}
	
	public List<Usuario> getUsuarios() {
		return usuarioDAO.getUsuariosRegistrados();
	}
	
	public List<Usuario> getUsuariosPendientes(){
		return usuarioDAO.getUsuariosPendientes();
	}
	
	public Usuario obtenerUsuarioCedula(String cedula) {
		return usuarioDAO.obtenerUsuario(cedula);
	}
}
