package ar.edu.unju.edm.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.edm.app.dao.IUserDAO;
import ar.edu.unju.edm.app.model.Rol;
import ar.edu.unju.edm.app.model.UserBD;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService { /* Guarda el usuario con el que se quiere trabajar */
																   // Verifica el usuario con dni y pass, y mostrar el tipo de usuario
	@Autowired
	private IUserDAO userDao; 
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {	//UserDetails representa un usuario autenticado
		UserBD user = userDao.findById(Long.parseLong(dni)).orElseThrow(()->new UsernameNotFoundException("Login Invalido"));	
//		
		if(user == null) {
			logger.error("Error Login: no existe el usuario '" + dni +"'");
			throw new UsernameNotFoundException("DNI " + dni + "NO existe en el sistema");
		}
//		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();	//Obtener los roles uno por uno y registrarlos dentro de una lista de GrantedAuthority  
		
		for(Rol rol: user.getRoles())	//for each por cada rol de los roles que estan relacionados al usuario
		{
			logger.info("ROLLLLL: ".concat(rol.getRol())); //
			roles.add(new SimpleGrantedAuthority(rol.getRol()));	//registrando los roles del usuario a Spring
		}
//		
		if(roles.isEmpty()) {
			logger.error("Error Login: usuario '" + dni +"' no tiene roles asignados");
			throw new UsernameNotFoundException("Error Login: usuario '" + dni +"' no tiene roles asignados");
		}
//		
		return new User(dni, user.getContrasena(), user.getEnabled(), true, true, true, roles);	//retorna un usuario autenticado con los respectivos campos
	}

}
//		1. Se carga la configuracion de de SecurityConfig
//		2. Cuando se cargan los users de import.sql, SpringSecurity crea el objeto
//		3. loadByUsername recibe el nombre de usuario, crea un objeto JpaUserDetailsService con el nombre enviado y los roles