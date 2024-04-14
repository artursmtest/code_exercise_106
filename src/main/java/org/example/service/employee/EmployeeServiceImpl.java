package org.example.service.employee;

import java.util.ArrayList;
import java.util.List;

import org.example.dto.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public List<Employee> linkSubordinatesToManager(List<Employee> employees) {

        if (employees != null && !employees.isEmpty()) {
            for (Employee employee : employees) {
                int managerId = employee.getId();
                List<Employee> subordinates = employees.stream()
                        .filter(employeeWithAllRoles -> employeeWithAllRoles.getManagerId() != 0)
                        .filter(employeeNotACeo -> employeeNotACeo.getManagerId() == managerId)
                        .toList();
                employee.setSubordinates(subordinates);
            }
            return employees;
        }
        return new ArrayList<>();
    }
}
