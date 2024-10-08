package org.java.spring.pizzeria.repo;

import java.util.List;

import org.java.spring.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> 
{
	// Provides all basic CRUD operations
	// With the option to add custom methods and features if needed
	
	public List<Pizza> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
	
	
}
