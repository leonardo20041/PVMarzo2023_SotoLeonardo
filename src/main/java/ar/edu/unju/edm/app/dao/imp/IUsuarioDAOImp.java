package ar.edu.unju.edm.app.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.edm.app.dao.IUsuarioDAO;
import ar.edu.unju.edm.app.model.Usuario;

@Repository
public class IUsuarioDAOImp implements IUsuarioDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findAll() {
		return em.createQuery("from Usuario").getResultList();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		
		if(usuario.getDni() != null && usuario.getDni() > 0)
		{
			em.merge(usuario);	//actualizar
		}
		else
		{
			em.persist(usuario);
		}
				
	}

	@Override
	public Usuario findOne(Long dni) {		
		return em.find(Usuario.class, dni);
	}

}
