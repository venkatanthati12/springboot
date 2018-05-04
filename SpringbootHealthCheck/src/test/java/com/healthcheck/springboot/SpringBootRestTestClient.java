package com.healthcheck.springboot;
 
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.healthcheck.springboot.model.Employee;
 

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/HealthCheck/api";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllEmployees(){
        System.out.println("Testing listAllEmployees API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> empMap = restTemplate.getForObject(REST_SERVICE_URI+"/rest/employee", List.class);
         
        if(empMap!=null){
            for(LinkedHashMap<String, Object> map : empMap){
                System.out.println("Employee : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No Employee exist----------");
        }
    }
     
    /* GET */
    private static void getEmployee(){
        System.out.println("Testing getEmployee API----------");
        RestTemplate restTemplate = new RestTemplate();
        Employee user = restTemplate.getForObject(REST_SERVICE_URI+"/rest/employee/1", Employee.class);
        System.out.println(user);
    }
     
    /* POST */
    private static void createEmployee() {
        System.out.println("Testing create Employee API----------");
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = new Employee(0,"Sarah",51,134,"Yes");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/rest/employee/dummy", emp, Employee.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateEmployee() {
        System.out.println("Testing update Employee API----------");
        RestTemplate restTemplate = new RestTemplate();
        Employee emp  = new Employee(1,"Tomy",33, 70000,"No");
        restTemplate.put(REST_SERVICE_URI+"//rest/employee/1", emp);
        System.out.println(emp);
    }
 
    /* DELETE */
    private static void deleteEmployee() {
        System.out.println("Testing delete Employee API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/rest/employee/delete/3");
    }
 
    public static void main(String args[]){
        listAllEmployees();
        getEmployee();
        createEmployee();
        listAllEmployees();
        updateEmployee();
        listAllEmployees();
        deleteEmployee();
        listAllEmployees();
    }
}