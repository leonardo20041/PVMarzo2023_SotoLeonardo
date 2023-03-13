package ar.edu.unju.edm.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unju.edm.app.model.Habitacion;
import ar.edu.unju.edm.app.model.Usuario;

public interface IHabitacionDAO extends CrudRepository<Habitacion, Long> {

	@Query("SELECT h FROM Habitacion h WHERE h.codigo LIKE ?1")
	public List<Habitacion> findByCodigoHabitacion(Long codigo);
}
