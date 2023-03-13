package ar.edu.unju.edm.app.service;

import java.util.List;

import ar.edu.unju.edm.app.model.Habitacion;

public interface IHabitacionService {
	
	public List<Habitacion> findAllByEstado(Long codigo);

	public List<Habitacion> findAll();
	public Habitacion findOne(Long codigo);
	public Habitacion listById(Long codigo);
}
