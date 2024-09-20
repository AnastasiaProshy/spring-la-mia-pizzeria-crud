package org.java.spring.pizzeria.controller;

import java.util.List;

import org.java.spring.pizzeria.model.Pizza;
import org.java.spring.pizzeria.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
	
	@Controller
	@RequestMapping("/pizzas")
	public class PizzaController 
	{
		@Autowired
		private PizzaRepository repo;
		
		// repository field con autowired per d.i.
		
		
		@GetMapping()
		public String index(Model model, @RequestParam(required = false) String search)
		{
			model.addAttribute("pizzaSearch", search);
			
			List<Pizza> pizzasList;
			
			// take the data to deliver to index 
			if (search != null && !search.isEmpty())
			{
				model.addAttribute("pizzaSearch", search);
				pizzasList = repo.findByNameContainingIgnoreCaseOrderByNameAsc(search);
			}
			else
			{
				pizzasList = repo.findAll(Sort.by("name"));
			}		
			
			// insert them into the model
			model.addAttribute("pizzas", pizzasList);
			
			return "/pizzas/index";
		}
		
		
		@GetMapping ("/{id}")
		public String show (@PathVariable Integer id,  Model model)
		{
			model.addAttribute("pizza", repo.findById(id).get());
			return "/pizzas/show";
		}
		
		
//		@GetMapping ("/findByName/{name}")
//		public String findByName(@PathVariable("name") String name, Model model)
//		{
//			model.addAttribute("pizzas", repo.findByNameContains(name));
//			return "/pizzas/index";
//		}
		
		
		
		//CREATE
		
		@GetMapping("create")
		public String create(Model model)
		{
			model.addAttribute("pizza", new Pizza());
			return"/pizzas/create";
		}
		
		
		//STORE
		
		@PostMapping("/create")
		public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, 
							BindingResult bindingResult,
							RedirectAttributes attributes,
							Model model)
		{
			if(bindingResult.hasErrors())
			{
				return "/pizzas/create";
			}
			else
			{
				//data+
				repo.save(formPizza);
				
				attributes.addFlashAttribute("typeAlert", "success");
				attributes.addFlashAttribute("messageAlert", formPizza.getName() + " has been added successfully");
				
				return "redirect:/pizzas";
			}
		}
	
		
		//EDIT
		
		@GetMapping ("edit/{id}")
		public String edit(@PathVariable("id") Integer id, Model model)
		{
			model.addAttribute("pizza", repo.findById(id).get());	
			//Pizza pizzaToEdit = repo.findById(id).get();
			//model.addAttribute("pizza", pizzaToEdit);	
			
			return "/pizzas/edit";
		}
		
		
		//UPDATE
		
		@PostMapping("/edit/{id}")
		public String update(@Valid @ModelAttribute("pizza") Pizza updatedFormPizza,
							BindingResult bindingResult,
							RedirectAttributes attributes,
							Model model)
		{
			if (bindingResult.hasErrors())
			{
				return "/pizza/edit";
			}
			else
			{
				repo.save(updatedFormPizza);
				
				attributes.addFlashAttribute("typeAlert", "info");
				attributes.addFlashAttribute("messageAlert", "Great news! '" + updatedFormPizza.getName() + "' has been updated successfully");
				
				return"redirect:/pizzas";
			}
		}
		
		
		//DELETE
		@PostMapping("/delete/{id}")
		public String delete(@PathVariable("id") Integer id,
							RedirectAttributes attributes)
		{	
			Pizza pizzaToDelete = repo.findById(id).get();
			
			repo.deleteById(id);
			
			attributes.addFlashAttribute("typeAlert", "danger");
			attributes.addFlashAttribute("messageAlert", "Great news! '" + pizzaToDelete.getName() + "' has been deleted successfully");
				
			return "redirect:/pizzas";
		}
		
		
	}
