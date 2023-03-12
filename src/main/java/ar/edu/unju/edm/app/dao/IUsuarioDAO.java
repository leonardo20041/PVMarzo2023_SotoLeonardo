package ar.edu.unju.edm.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unju.edm.app.model.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
	
	@Query("SELECT u FROM Usuario u WHERE u.dni LIKE ?1")
	public List<Usuario> findByDni(Long palabraDni);
	
	@Query("SELECT u FROM Usuario u WHERE u.nacionalidad LIKE ?1")
	public List<Usuario> findByNacionalidad(String palabraNacion);
	
	@Query("SELECT u FROM Usuario u WHERE u.fechaNacimiento LIKE ?1")
	public List<Usuario> findByFechaNacimiento(Date palabraNacimiento);
}
