package ar.edu.unju.edm.app.service;

import java.util.List;

import ar.edu.unju.edm.app.model.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll(String palabraClave);
	
	public List<Usuario> findAllByDni(String palabraDni); //
	public List<Usuario> findAllByNacionalidad(String palabraNacion);
	public List<Usuario> findAllByFechaNacimiento(String palabraNacimiento);
	
	public List<Usuario> findAllByDni2(String dniSt);
	public List<Usuario> findAllByNacionalidad2(String nacionalidadSt);
	public List<Usuario> findAllByFechaNacimiento2(String nacimientoSt);
	
//	public List<Usuario> findAll();
	public void save(Usuario usuario);
	public Usuario findOne(Long dni);
	public void delete(Long dni);
}
