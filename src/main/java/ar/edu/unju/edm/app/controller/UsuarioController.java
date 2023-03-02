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

import ar.edu.unju.edm.app.dao.IUsuarioDAO;
import ar.edu.unju.edm.app.model.Usuario;

@Controller
@SessionAttributes("cliente")	//en una peticion GET obtiene usuario y todos los datos persisten hasta que se envie al metodo guardar
public class UsuarioController {

	@Autowired
	private IUsuarioDAO usuarioDao;
	
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
		model.addAttribute("usuarios", usuarioDao.findAll());
		return "listarUsuarios";
	}

	@GetMapping("/form")
	public String crearUsuarios(Model model)
	{
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Formulario de Usuario");
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status)
	{
		if(result.hasErrors())
		{
			model.addAttribute("titulo", "Formulario de Usuario");
			return "form";
		}
		
		usuarioDao.save(usuario);
		status.setComplete();
		return "redirect:/listarUsuarios";
	}
	
	@GetMapping("/form/{dni}")
	public String editar(@PathVariable(name = "dni") Long dni, Model model)
	{
		Usuario usuario = null;
		
		if(dni > 0)
		{
			usuario = usuarioDao.findOne(dni);
		}
		else
		{
			return "redirect:/listarUsuarios";
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Editar Usuario");
		return "form";
	}
}