package ar.edu.unju.edm.app.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.edm.app.dao.IUsuarioDAO;
import ar.edu.unju.edm.app.model.Usuario;
import ar.edu.unju.edm.app.service.IUsuarioService;

@Service	//Unico punto de acceso hacia distintos DAO
public class UsuarioServiceImp implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {		
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long dni) {		
		return usuarioDao.findById(dni).orElse(null);
	}
	
	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
	}


	@Override
	@Transactional
	public void delete(Long dni) {
		usuarioDao.deleteById(dni);
	}

}
