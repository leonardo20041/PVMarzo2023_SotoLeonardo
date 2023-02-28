package ar.edu.unju.edm.app.dao;

import java.util.List;

import ar.edu.unju.edm.app.model.Usuario;

public interface IUsuarioDAO {

	public List<Usuario> findAll();
	public void save(Usuario usuario);
}
