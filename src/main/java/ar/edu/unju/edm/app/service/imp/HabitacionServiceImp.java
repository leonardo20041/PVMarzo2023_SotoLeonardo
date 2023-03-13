package ar.edu.unju.edm.app.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.edm.app.dao.IHabitacionDAO;
import ar.edu.unju.edm.app.dao.IUsuarioDAO;
import ar.edu.unju.edm.app.model.Habitacion;
import ar.edu.unju.edm.app.model.Usuario;
import ar.edu.unju.edm.app.service.IHabitacionService;

@Service
public class HabitacionServiceImp implements IHabitacionService {

	@Autowired
	IHabitacionDAO habitacionDao;
	
	@Autowired
	IUsuarioDAO usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Habitacion> findAll() {		
//		List<Usuario> usuarioCop = new ArrayList<>();
//		List<Habitacion> copia = new ArrayList<>();
//		List<Habitacion> encontrado = new ArrayList<>();
//		
//		usuarioCop = (List<Usuario>) usuarioDao.findAll();
//		copia = (List<Habitacion>) habitacionDao.findAll();
//		
//		for(int i=0; i<copia.size(); i++)
//		{
//			for(int j=0; )
//			if(copia.get(i).getCodigo() == usuarioCop.get(i).getHabitacion().getCodigo())
//			{
//				encontrado.add(copia.get(i));
//			}
//		}
//		
//		return encontrado;
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

	@Override
	public List<Habitacion> findAllByEstado(Long codigo) {
		
		return null;
	}
}
