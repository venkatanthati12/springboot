package com.healthcheck.springboot.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.healthcheck.springboot.model.Employee;



@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Employee> emps;
	
	static{
		emps= populateDummyEmployees();
	}

	public List<Employee> findAllEmployee() {
		return emps;
	}
	
	public Employee findById(long id) {
		for(Employee emp : emps){
			if(emp.getId() == id){
				return emp;
			}
		}
		return null;
	}
	
	public Employee findByName(String name) {
		for(Employee emp : emps){
			if(emp.getName().equalsIgnoreCase(name)){
				return emp;
			}
		}
		return null;
	}
	
	public void saveEmployee(Employee emp) {
		emp.setId(counter.incrementAndGet());
		emps.add(emp);
	}

	public void updateEmployee(Employee emp) {
		int index = emps.indexOf(emp);
		emps.set(index, emp);
	}

	public void deleteEmployeeById(long id) {
		
		for (Iterator<Employee> iterator = emps.iterator(); iterator.hasNext(); ) {
		    Employee emp = iterator.next();
		    if (emp.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isEmployeeExist(Employee emp) {
		return findByName(emp.getName())!=null;
	}
	
	public void deleteAllEmployees(){
		emps.clear();
	}

	private static List<Employee> populateDummyEmployees(){
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(new Employee(counter.incrementAndGet(),"Sam",30, 70000, "Yes"));
		emps.add(new Employee(counter.incrementAndGet(),"Tom",40, 50000, "No"));
		emps.add(new Employee(counter.incrementAndGet(),"Jerome",45, 30000, "Yes"));
		emps.add(new Employee(counter.incrementAndGet(),"Silvia",50, 40000, "Yes"));
		return emps;
	}

}
