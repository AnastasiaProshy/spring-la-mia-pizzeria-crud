package org.java.spring.pizzeria.controller;

import java.util.List;

import org.java.spring.pizzeria.model.Pizza;
import org.java.spring.pizzeria.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController 
{
	@Autowired
	private PizzaRepository repo;
	
	// repository field con autowired per d.i.
	
	@GetMapping
	public String index(Model model)
	{
		// take the data to deliver to books/index
		List<Pizza> pizzas = repo.findAll();
		
		// insert them into the model
		model.addAttribute("pizzas", pizzas);
		
		return "/pizzas/index";
	}
	
}
