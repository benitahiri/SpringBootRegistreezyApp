package net.javaguides.springboot.service;

import java.time.LocalDate;
import java.util.List;

import net.javaguides.springboot.model.Employee;

public interface EmployeeService {
	
       Employee saveEmployee(Employee employee);
       List<Employee> getAllEmployees();
       Employee getEmployeeById(long id);
       Employee updateEmployee(Employee employee, long id);
       void deleteEmployee(long id);
	List<Employee> getEmployeesByRegistrationDateRange(LocalDate startDate, LocalDate endDate);

	
}
