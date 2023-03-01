package ar.edu.unju.edm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.edm.app.dao.IUsuarioDAO;
import ar.edu.unju.edm.app.model.Usuario;

@Controller
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
	public String guardar(Usuario usuario)
	{
		usuarioDao.save(usuario);
		return "redirect:/listarUsuarios";
	}
}