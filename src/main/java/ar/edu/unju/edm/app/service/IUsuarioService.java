package ar.edu.unju.edm.app.service;

import java.util.List;

import ar.edu.unju.edm.app.model.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll(String palabraClave);
	
//	public List<Usuario> findAll();
	public List<Usuario> findAllByDni(String palabraDni); //
	public List<Usuario> findAllByNacionalidad(String palabraNacion);
	public List<Usuario> findAllByFechaNacimiento(String palabraNacimiento);
	
//	public List<Usuario> findAll();
	public void save(Usuario usuario);
	public Usuario findOne(Long dni);
	public void delete(Long dni);
	public boolean isNumeric(String cadena);
}
