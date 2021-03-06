package com.pma.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pma.dao.EmployeeRepository;
import com.pma.entities.Employee;

@RestController
@RequestMapping("/app-api/employees")
public class EmployeeApiController {

	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping
	public List<Employee> getEmployees(){
		return empRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable ("id") Long id) {
		
		 return empRepo.findById(id).get();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee create(@RequestBody Employee employee) {
		return empRepo.save(employee);
	}
	
	// affects relation project-employee , deletes row !!!
	@PutMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Employee update(@RequestBody Employee employee){
		return empRepo.save(employee);
	}
	
	
	@PatchMapping(path="/{id}", consumes = "application/json")
	public Employee partialUpdate(@RequestBody Employee patchEmployee, @PathVariable ("id") Long id ){
		Employee emp = empRepo.findById(id).get();
		if(patchEmployee.getFirstName() !=null) {
			emp.setFirstName(patchEmployee.getFirstName());
		}
		if(patchEmployee.getLastName() !=null) {
			emp.setLastName(patchEmployee.getLastName());
		}
		if(patchEmployee.getEmail() !=null) {
			emp.setEmail(patchEmployee.getEmail());
		}
		return empRepo.save(emp);
	}
	
	
	@DeleteMapping(path="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable ("id") Long id) {
		try {
			empRepo.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			
		}
		
	}
		 
}
