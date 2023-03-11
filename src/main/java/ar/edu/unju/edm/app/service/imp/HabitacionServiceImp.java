package ar.edu.unju.edm.app.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.edm.app.dao.IHabitacionDAO;
import ar.edu.unju.edm.app.model.Habitacion;
import ar.edu.unju.edm.app.service.IHabitacionService;

@Service
public class HabitacionServiceImp implements IHabitacionService {

	@Autowired
	IHabitacionDAO habitacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Habitacion> findAll() {		
		return (List<Habitacion>) habitacionDao.findAll();
	}

	@Override
	public Habitacion findOne(Long codigo) {
		return habitacionDao.findById(codigo).orElse(null);
	}

	@Override
	public Habitacion listById(Long codigo) {
		return habitacionDao.findById(codigo).orElse(null);
	}
}
