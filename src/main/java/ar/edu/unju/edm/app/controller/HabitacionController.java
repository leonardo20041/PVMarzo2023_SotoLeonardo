package ar.edu.unju.edm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.edu.unju.edm.app.service.IHabitacionService;

@Controller
public class HabitacionController {

	@Autowired
	IHabitacionService habitacionService;
	
	@GetMapping("/listarRegulares")
	public String listarRegulares(Model model)
	{
		model.addAttribute("titulo", "Habitaciones Regulares Disponibles");
		model.addAttribute("habitaciones", habitacionService.findAll());
		return "listarHabitaciones";
	}
	
	@GetMapping("/listarPremiums")
	public String listarPremiums(Model model)
	{
		model.addAttribute("titulo", "Habitaciones Premiums Disponibles");
		model.addAttribute("habitaciones", habitacionService.findAll());
		return "listarHabitaciones";
	}
}
