package ar.edu.unju.edm.app.controller;

import java.util.Arrays;
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
	
	@GetMapping("/listarUsuarios")
	public String listarUsuarios(Model model, @Param("dniSt") String dniSt, @Param("nacionalidadSt") String nacionalidadSt, @Param("nacimientoSt") String nacimientoSt, RedirectAttributes flash)
	{	
		if(dniSt != null)
		{
			if(dniSt != "")
			{
				if(usuarioService.findAllByDni(dniSt) != null)
				{
					model.addAttribute("usuarios", usuarioService.findAllByDni(dniSt));
				}
				else
				{
					flash.addFlashAttribute("error", "Tipo de Dato Incorrecto");
					return "redirect:/listarUsuarios";
				}
			}			
		}
		
		if(nacionalidadSt != null)
		{
			if(nacionalidadSt != "") 
			{
				model.addAttribute("palabraFiltrada", nacionalidadSt);
				model.addAttribute("usuarios", usuarioService.findAllByNacionalidad(nacionalidadSt));
			}			
		}
		
		if(nacimientoSt != null)
		{
			if(nacimientoSt != "")
			{
				if(usuarioService.findAllByFechaNacimiento(nacimientoSt) != null)
				{			
					model.addAttribute("usuarios", usuarioService.findAllByFechaNacimiento(nacimientoSt));
				}
				else
				{
					flash.addFlashAttribute("error", "Formato Incorrecto, intente: 'yyyy/MM/dd'");
					return "redirect:/listarUsuarios";
				}
			}			
		}
		else
		{
			model.addAttribute("usuarios", usuarioService.findAll());
		}
		
		model.addAttribute("titulo", "Listado de Huespedes Registrados");	
		return "listarUsuarios";
	}
	
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
//		Usuario busqueda = usuarioService.findOne(usuario.getDni());
		
		if(result.hasErrors())
		{
			model.addAttribute("titulo", "Formulario de Usuario");
			
			String boton = (usuario.getDni() != null)? "Guardar Usuario" : "Crear Usuario";
			model.addAttribute("botonSubmit", boton);			
			logger.info("IIIIIIIIIIIIIIIIIIIIIIIIIIFFFFFFFFFFFFF");
			return "form";
		}
		
//		if(busqueda != null)
//		{
//			if(usuario.getDni() == busqueda.getDni())
//			{
//				model.addAttribute("titulo", "Formulario de Usuario");
//				
//				String boton = (usuario.getDni() != null)? "Guardar Usuario" : "Crear Usuario";
//				flash.addFlashAttribute("error", "Ya existe un Usuario con este DNI");
//				model.addAttribute("botonSubmit", boton);			
//				logger.info("IGUALLLLLLLLLLLLLLLLL");
//				return "redirect:/form";
//			}
//		}			
		
		String mensajeFlash = (usuario.getDni() != 0)? "Usuario creado con exito" : "Usuario editado con exito";
		logger.info("DNI ESSS:" + usuario.getDni());
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
}