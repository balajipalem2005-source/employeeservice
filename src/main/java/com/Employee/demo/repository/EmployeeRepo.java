package com.Employee.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Employee.demo.Entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}