package ar.edu.unju.edm.app.dao;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unju.edm.app.model.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
	
}
