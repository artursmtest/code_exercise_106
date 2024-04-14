package org.example.service.employee;

import java.util.List;

import org.example.dto.Employee;

public interface EmployeeService {

    List<Employee> linkSubordinatesToManager(List<Employee> employees);

}
