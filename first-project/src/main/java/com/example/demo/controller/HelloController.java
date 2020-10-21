package com.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
public class HelloController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/hi")
	public String sayAhla() {
		return "ahla w sahla";
	}

	@PostMapping("/add-emp")
	public void addEmplyee() {
		employeeRepository.save(new Employee("tarek"));
	}

	@PostMapping("/")
	public ResponseEntity<Employee> create(@RequestBody Employee employee) throws URISyntaxException {
		Employee createdEmployee = employeeRepository.save(employee);
		if (createdEmployee == null) {
			return ResponseEntity.notFound().build();
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdEmployee.getId()).toUri();

			return ResponseEntity.created(uri).body(createdEmployee);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Employee>> read(@PathVariable("id") Long id) {
		Optional<Employee> foundEmployee = employeeRepository.findById(id);
		if (foundEmployee == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(foundEmployee);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> update(@RequestBody Employee employee, @PathVariable Long id) {
		Employee updatedEmployee = employeeRepository.saveAndFlush(employee);
		if (updatedEmployee == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(updatedEmployee);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
		employeeRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
