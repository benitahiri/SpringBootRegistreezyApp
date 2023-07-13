package net.javaguides.springboot.repository;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;




public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	
	

	    List<Employee> findByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
	

}



