package org.example.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.dto.Employee;
import org.example.service.employee.EmployeeService;

public class EmployeeServiceMock implements EmployeeService {

    public static boolean emptyList = false;

    @Override
    public List<Employee> linkSubordinatesToManager(List<Employee> employees) {
        if (emptyList) {
            return Collections.emptyList();
        } else {
            List<Employee> testEmployees = new ArrayList<>();
            testEmployees.add(new Employee(1, "John", "Doe", BigDecimal.valueOf(5000), 0));
            testEmployees.add(new Employee(2, "Jack", "Dong", BigDecimal.valueOf(3000), 1));
            testEmployees.add(new Employee(3, "Bob", "Chack", BigDecimal.valueOf(4000), 1));
            testEmployees.get(0).setSubordinates(List.of(testEmployees.get(1), testEmployees.get(2)));
            return testEmployees;
        }
    }
}
