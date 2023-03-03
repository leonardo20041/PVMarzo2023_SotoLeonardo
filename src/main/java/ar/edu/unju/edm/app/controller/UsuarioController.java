package ar.edu.unju.edm.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unju.edm.app.model.Usuario;
import ar.edu.unju.edm.app.service.IUsuarioService;

@Controller
@SessionAttributes("cliente")	//en una peticion GET obtiene usuario y todos los datos persisten hasta que se envie al metodo guardar
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping({"/", "/index", ""})
	public String index(Model model)
	{
		model.addAttribute("titulo", "Hotel Spring");
		return "index";
	}
	
	@GetMapping("/listarUsuarios")
	public String listarUsuarios(Model model)
	{
		model.addAttribute("titulo", "Listado de Huespedes Registrados");
		model.addAttribute("usuarios", usuarioService.findAll());
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
}