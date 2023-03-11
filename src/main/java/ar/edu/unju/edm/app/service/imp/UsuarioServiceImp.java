package ar.edu.unju.edm.app.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.edm.app.dao.IUsuarioDAO;
import ar.edu.unju.edm.app.model.Usuario;
import ar.edu.unju.edm.app.service.IUsuarioService;

@Service	//Unico punto de acceso hacia distintos DAO
public class UsuarioServiceImp implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDao;
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<Usuario> findAll() {	
//		List<Usuario> copia = new ArrayList<>();
//		List<Usuario> resultado = new ArrayList<>();
//		copia = (List<Usuario>) usuarioDao.findAll();
//		
//		for(int i=0; i<copia.size(); i++)
//		{
//			if(copia.get(i).getDni() == dni)
//		}
//		
//		return (List<Usuario>) usuarioDao.findAll();
//	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll(String palabraClave) {	
		
		if(palabraClave != null)
		{
			return usuarioDao.findByFilter(palabraClave);
		}
		
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long dni) {		
		return usuarioDao.findById(dni).orElse(null);
	}
	
	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
	}


	@Override
	@Transactional
	public void delete(Long dni) {
		usuarioDao.deleteById(dni);
	}

	@Override
	public List<Usuario> findAllByDni(String palabraDni) {
		if(palabraDni != null)
		{
			return usuarioDao.findByDni(Long.parseLong(palabraDni));	//retorna tipo long
		}
		return null;
	}

	@Override
	public List<Usuario> findAllByNacionalidad(String palabraNacion) {
		if(palabraNacion != null)
		{
			return usuarioDao.findByNacionalidad(palabraNacion); //retorna tipo String
		}
		return null;
	}

	@Override
	public List<Usuario> findAllByFechaNacimiento(String palabraNacimiento) {
		if(palabraNacimiento != null)
		{
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
			Date dataFormateada;
			try {
				dataFormateada = formato.parse(palabraNacimiento);	
				return usuarioDao.findByFechaNacimiento(dataFormateada); //retorna tipo Date
			} 
			
			catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		return null;
	}

	
	@Override
	public List<Usuario> findAllByDni2(String dniSt) {
		Long dni = Long.parseLong(dniSt);
		System.out.println(dni);
		List<Usuario> copia = new ArrayList<>();
		List<Usuario> encontrado = new ArrayList<>();
		
		copia = (List<Usuario>) usuarioDao.findAll();
		
		for(int i=0; i<copia.size(); i++)
		{
			if(copia.get(i).getDni() == dni)
			{
				encontrado.add(copia.get(i));
				System.out.println(encontrado.get(i).getDni());
			}
		}
		return encontrado;
	}

	@Override
	public List<Usuario> findAllByNacionalidad2(String nacionalidadSt) {
		List<Usuario> copia = new ArrayList<>();
		List<Usuario> encontrado = new ArrayList<>();
		
		copia = (List<Usuario>) usuarioDao.findAll();
		
		for(int i=0; i<copia.size(); i++)
		{
			if(copia.get(i).getNacionalidad() == nacionalidadSt)
			{
				encontrado.add(copia.get(i));
				System.out.println("ssss: " + encontrado.get(i).getNacionalidad());
			}
		}
		
		return encontrado;
	}

	@Override
	public List<Usuario> findAllByFechaNacimiento2(String nacimientoSt) {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		Date nacimiento = null;
		try {
				nacimiento = formato.parse(nacimientoSt);
			} 
		catch (ParseException e) {
				e.printStackTrace();
			}
		
		List<Usuario> copia = new ArrayList<>();
		List<Usuario> encontrado = new ArrayList<>();
		
		copia = (List<Usuario>) usuarioDao.findAll();
		
		for(int i=0; i<copia.size(); i++)
		{
			if(copia.get(i).getFechaNacimiento() == nacimiento)
			{
				encontrado.add(copia.get(i));
			}
		}
		
		return encontrado;
	}
}
