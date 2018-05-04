package com.healthcheck.springboot.service;


import java.util.List;

import com.healthcheck.springboot.model.Employee;

public interface EmployeeService {
	
	Employee findById(long id);
	
	Employee findByName(String name);
	
	void saveEmployee(Employee emp);
	
	void updateEmployee(Employee emp);
	
	void deleteEmployeeById(long id);

	List<Employee> findAllEmployee();
	
	void deleteAllEmployees();
	
	boolean isEmployeeExist(Employee emp);
	
}
