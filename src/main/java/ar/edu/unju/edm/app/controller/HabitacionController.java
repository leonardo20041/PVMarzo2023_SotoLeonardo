package ar.edu.unju.edm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.unju.edm.app.service.IHabitacionService;

@Controller
public class HabitacionController {

	@Autowired
	IHabitacionService habitacionService;
	
	@GetMapping("/listarRegular")
	public String listarRegulares(Model model)
	{		
		model.addAttribute("titulo", "Habitaciones Regulares Disponibles");
		model.addAttribute("habitaciones", habitacionService.findAll());
		model.addAttribute("tipoH", "Regular");
		return "listarHabitaciones";
	}
	
	@GetMapping("/listarPremium")
	public String listarPremiums(Model model)
	{
		model.addAttribute("titulo", "Habitaciones Premiums Disponibles");
		model.addAttribute("habitaciones", habitacionService.findAll());
		model.addAttribute("tipoH", "Premium");
		return "listarHabitaciones";
	}
}
