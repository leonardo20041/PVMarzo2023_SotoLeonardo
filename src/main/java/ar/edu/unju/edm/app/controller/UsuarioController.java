package ar.edu.unju.edm.app.controller;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unju.edm.app.model.Usuario;
import ar.edu.unju.edm.app.service.IUsuarioService;

@Controller
@SessionAttributes("usuario")	//en una peticion GET obtiene usuario y todos los datos persisten hasta que se envie al metodo guardar
public class UsuarioController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping({"/", "/index", ""})
	public String index(Model model, HttpServletRequest request)
	{		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			logger.info("Usuario autenticado, username: ".concat(auth.getName()));
		}
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tenes acceso"));
		}
		
		else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tenes acceso"));
		}
		
		
		model.addAttribute("titulo", "Hotel Spring");
		return "index";
	}
	
//	@GetMapping("/listarUsuarios")
//	public String listarUsuarios(Model model, @Param("palabraDni") Long palabraDni, @Param("palabraNacion") String palabraNacion, @Param("palabraNacimiento") Date palabraNacimiento, @Param("pal") String pal)
//	{	
//		String palabraFiltrada = null;
//			
//		System.out.println(palabraDni);
//		System.out.println(palabraNacion);
//		System.out.println(palabraNacimiento);
//			
//		if(palabraDni != null) {
//			palabraFiltrada = Long.toString(palabraDni);
//			model.addAttribute("palabraFiltrada", palabraFiltrada);
//			model.addAttribute("usuarios", usuarioService.findAllByDni(palabraFiltrada));
//		}
//		
//		if(palabraNacion != null) {
//			model.addAttribute("palabraFiltrada", palabraNacion);
//			model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(palabraNacion));
//		}
//		
//		if(palabraNacimiento != null) {
//			
//			model.addAttribute("palabraFiltrada", palabraFiltrada);
//			model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(palabraFiltrada));
////			LocalDate localDate = LocalDate.now();
////			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
////			palabraFiltrada = localDate.format(formatter);
//		}
//		
//		
////		if{
//		model.addAttribute("titulo", "Listado de Huespedes Registrados");
//		model.addAttribute("palabraFiltrada", palabraFiltrada);
//		model.addAttribute("usuarios", usuarioService.findAll(palabraFiltrada));
//		return "listarUsuarios";
////		}
//	}

	@GetMapping("/form")
	public String crearUsuarios(Model model)
	{
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Formulario de Usuario");
		model.addAttribute("botonSubmit", "Crear Usuario");
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash)
	{
		if(result.hasErrors())
		{
			model.addAttribute("titulo", "Formulario de Usuario");
			
			String boton = (usuario.getDni() != null)? "Guardar Usuario" : "Crear Usuario";
			model.addAttribute("botonSubmit", boton);						
			return "form";
		}
		
		String mensajeFlash = (usuario.getDni() != 0)? "Usuario creado con exito" : "Usuario editado con exito";
		usuarioService.save(usuario);
		status.setComplete();		
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listarUsuarios";
	}
	
	@GetMapping("/form/{dni}")
	public String editar(@PathVariable(name = "dni") Long dni, Model model, RedirectAttributes flash)
	{
		Usuario usuario = null;
		
		if(dni > 0)
		{
			usuario = usuarioService.findOne(dni);
			if(usuario == null) {
				flash.addFlashAttribute("error", "El DNI del usuario no existe en la Base de Datos");
				return "redirect:/listarUsuarios";
			}
		}
		else
		{
			flash.addFlashAttribute("error", "El DNI del usuario no puede ser cero");
			return "redirect:/listarUsuarios";
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Editar Usuario");
		model.addAttribute("botonSubmit", "Guardar Usuario");
		return "form";
	}
	
	@GetMapping("/eliminar/{dni}")
	public String eliminar(@PathVariable(name = "dni") Long dni, Model model, RedirectAttributes flash)
	{
		if(dni > 0)
		{
			usuarioService.delete(dni);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito");
		}
		
		return "redirect:/listarUsuarios";
	}
	
	@ModelAttribute("nacionalidades")
	public List<String> nacionalidades()
	{
		return Arrays.asList("Argentina", "Bolivia", "Brasil", "Colombia", "Chile", "Ecuador", 
				"England", "Guatemala", "Mexico", "Paraguay", "Perú", "Uruguay", "Venezuela");
	}
	
	@GetMapping("/listarUsuarios")
	public String listarUsuarios(Model model, @Param("palabraDni") String palabraDni, @Param("palabraNacion") String palabraNacion, @Param("palabraNacimiento") String palabraNacimiento, @Param("pal") String pal)
	{	
		String palabraFiltrada = null;
			
		System.out.println("DNI: " + palabraDni);
		System.out.println("NACIONALIDAD: " + palabraNacion);
		System.out.println("NACIMIENTO: " + palabraNacimiento);
		System.out.println("INFORMACION: " + pal);
			
		if(palabraDni != null || palabraDni != "") {
			model.addAttribute("palabraFiltrada", palabraDni);
			model.addAttribute("usuarios", usuarioService.findAllByDni(palabraDni));
			
//			model.addAttribute("palabraFiltrada", pal);
//			model.addAttribute("usuarios", usuarioService.findAllByDni(pal));
		}
		
		if(palabraNacion != null || palabraDni != "") {
			model.addAttribute("palabraFiltrada", palabraNacion);
			model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(palabraNacion));
			
//			model.addAttribute("palabraFiltrada", pal);
//			model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(pal));
		}
		
		if(palabraNacimiento != null || palabraDni != "") {
			model.addAttribute("palabraFiltrada", palabraNacimiento);
			model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(palabraNacimiento));
			
//			model.addAttribute("palabraFiltrada", pal);
//			model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(pal));
			
//			LocalDate localDate = LocalDate.now();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//			palabraFiltrada = localDate.format(formatter);
		}
		
		else {
			model.addAttribute("titulo", "Listado de Huespedes Registrados");
			model.addAttribute("palabraFiltrada", palabraFiltrada);
			model.addAttribute("usuarios", usuarioService.findAll(palabraFiltrada));
		}
		
		model.addAttribute("titulo", "Listado de Huespedes Registrados");
		
		System.out.println("DNI: " + palabraDni);
		System.out.println("NACIONALIDAD: " + palabraNacion);
		System.out.println("NACIMIENTO: " + palabraNacimiento);
		System.out.println("INFORMACION: " + pal);
		
		return "listarUsuarios";
	}
}