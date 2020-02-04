package datos;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Usuario;

@Stateless
public class UsuarioDAO {

	@Inject
	private EntityManager em;

	public void insert(Usuario usuario) {
		usuario.setEstado_usuario("Inhabilitado");
		em.persist(usuario);
	}

	public void update(Usuario usuario) {
		em.merge(usuario);
	}

	public void remove(String cedula) {
		em.remove(this.read(cedula));
	}

	public Usuario read(String cedula) {
		return em.find(Usuario.class, cedula);
	}
	
	//Retorna los usuarios que no sean admin
	public Usuario obtenerUsuario(String cedula) {
		try {
			String jpql = "SELECT u FROM Usuario u WHERE cedula_usuario = ?1 AND estado_usuario <> 'Admin'";
			Query q = em.createQuery(jpql, Usuario.class);
			q.setParameter(1, cedula);
			Usuario u = (Usuario) q.getSingleResult();
			return u;
		} catch (Exception e) {
			return null;
		}
	}

	//Obtener usuarios HABILITADOS
	public List<Usuario> getUsuariosRegistrados() {
		String jpql = "SELECT u FROM Usuario u WHERE estado_usuario <> 'Inhabilitado' and estado_usuario <> 'Pendiente' and estado_usuario <> 'Denegado'";
		Query q = em.createQuery(jpql, Usuario.class);
		List<Usuario> usuarios = q.getResultList();
		return usuarios;
	}
	
	//Obtener usuarios que estan PENDIENTES DE APROBACION
	public List<Usuario> getUsuariosPendientes() {
		String jpql = "SELECT u FROM Usuario u WHERE estado_usuario = 'Inhabilitado'";
		Query q = em.createQuery(jpql, Usuario.class);
		List<Usuario> usuarios = q.getResultList();
		return usuarios;
	}
	
	public List<Usuario> getUsuarioCedula(String cedula) {
		String jpql = "SELECT u FROM Usuario u WHERE cedula_usuario = ?1";
		Query q = em.createQuery(jpql, Usuario.class);
		q.setParameter(1, cedula);
		List<Usuario> usuarios = q.getResultList();
		return usuarios;
	}
	
	public void habilitarUsuario(String cedulaUsuario) {
		Query cambiarEstado = em.createQuery("UPDATE Usuario c SET estado_usuario = 'Habilitado' WHERE cedula_usuario = ?1");
		cambiarEstado.setParameter(1, cedulaUsuario);
		cambiarEstado.executeUpdate();
	}
	
	public void aprobarUsuario(String cedulaUsuario) {
		Query cambiarEstado = em.createQuery("UPDATE Usuario c SET estado_usuario = 'Pendiente' WHERE cedula_usuario = ?1");
		cambiarEstado.setParameter(1, cedulaUsuario);
		cambiarEstado.executeUpdate();
	}
	
	public void denegarUsuario(String cedulaUsuario) {
		Query cambiarEstado = em.createQuery("UPDATE Usuario c SET estado_usuario = 'Denegado' WHERE cedula_usuario = ?1");
		cambiarEstado.setParameter(1, cedulaUsuario);
		cambiarEstado.executeUpdate();
	}
}