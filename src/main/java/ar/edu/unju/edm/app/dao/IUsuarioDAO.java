package ar.edu.unju.edm.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unju.edm.app.model.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
	
	public List<Usuario> findByDni(Long palabraDni);
	
	public List<Usuario> findByNacionalidad(String palabraNacion);
	
	public List<Usuario> findByFechaNacimiento(Date palabraNacimiento);
	
}
