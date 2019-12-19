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
	
	public List<Usuario> getUsuario(){
		String jpql = "SELECT u FROM Usuario u";
		Query q = em.createQuery(jpql, Usuario.class);
		
		List<Usuario> usuarios = q.getResultList();
		
		return usuarios;
	}
	
	public List<Usuario> getUsuarioCedula(String cedula){
		String jpql = "SELECT u FROM Usuario u WHERE cedula LIKE ?1";
		Query q = em.createQuery(jpql, Usuario.class);
		q.setParameter(1, "%" + cedula + "%");
		List<Usuario> usuarios= q.getResultList();
		return usuarios;
	}
}
