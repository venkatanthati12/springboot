package com.healthcheck.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.healthcheck.springboot.model.Employee;
import com.healthcheck.springboot.service.EmployeeService;
import com.healthcheck.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	EmployeeService empService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Employee---------------------------------------------

	@RequestMapping(value = "/rest/employee", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> listAllEmployees() {
		List<Employee> emps = empService.findAllEmployee();
		if (emps.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Employee>>(emps, HttpStatus.OK);
	}

	// -------------------Retrieve Single Employee------------------------------------------

	@RequestMapping(value = "/rest/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployee(@PathVariable("id") long id) {
		logger.info("Fetching Employee with id {}", id);
		Employee emp = empService.findById(id);
		if (emp == null) {
			logger.error("Employee with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Employee with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}

	// -------------------Create a Employee-------------------------------------------

	@RequestMapping(value = "/rest/employee/dummy", method = RequestMethod.POST)
	public ResponseEntity<?> createEmployee(@RequestBody Employee emp, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Employee : {}", emp);

		if (empService.isEmployeeExist(emp)) {
			logger.error("Unable to create. A Employee with name {} already exist", emp.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Employee with name " + 
					emp.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		empService.saveEmployee(emp);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(emp.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Employee ------------------------------------------------

	@RequestMapping(value = "/rest/employee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEmployee(@PathVariable("id") long id, @RequestBody Employee emp) {
		logger.info("Updating Employee with id {}", id);

		Employee currentEmployee = empService.findById(id);

		if (currentEmployee == null) {
			logger.error("Unable to update. Employee with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Employee with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentEmployee.setName(emp.getName());
		currentEmployee.setAge(emp.getAge());
		currentEmployee.setSalary(emp.getSalary());

		empService.updateEmployee(currentEmployee);
		return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
	}

	// ------------------- Delete a Employee-----------------------------------------

	@RequestMapping(value = "/rest/employee/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Employee with id {}", id);

		Employee emp = empService.findById(id);
		if (emp == null) {
			logger.error("Unable to delete. Employee with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Employee with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		empService.deleteEmployeeById(id);
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}

}