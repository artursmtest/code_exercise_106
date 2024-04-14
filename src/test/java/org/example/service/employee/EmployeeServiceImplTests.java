package org.example.service.employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import org.example.dto.Employee;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeServiceImplTests extends TestCase {

    private EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeService = new EmployeeServiceImpl();
    }

    @Test
    public void testLinkSubordinatesToManager() {
        List<Employee> employeeList = createTestEmployeeListWithSubordinates();

        var result = employeeService.linkSubordinatesToManager(employeeList);

        assertThat(result.get(0).getSubordinates().size(), equalTo(2));
        assertTrue(result.get(1).getSubordinates().isEmpty());
        assertTrue(result.get(2).getSubordinates().isEmpty());
    }

    @Test
    public void testLinkSubordinatesToManagerWithoutExistingSubordinates() {
        List<Employee> employeeList = createTestEmployeeListWithoutSubordinates();

        var result = employeeService.linkSubordinatesToManager(employeeList);

        assertTrue(result.get(0).getSubordinates().isEmpty());
        assertTrue(result.get(1).getSubordinates().isEmpty());
        assertTrue(result.get(2).getSubordinates().isEmpty());
    }

    @Test
    public void testLinkSubordinatesToManagerEmptyList() {
        List<Employee> emptyList = new ArrayList<>();

        var result = employeeService.linkSubordinatesToManager(emptyList);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testLinkSubordinatesToManagerNullList() {
        List<Employee> nullList = null;

        var result = employeeService.linkSubordinatesToManager(nullList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private List<Employee> createTestEmployeeListWithSubordinates() {
        return List.of(new Employee(1, "TestName", "TestLastName",
                        BigDecimal.valueOf(10000L)),
                new Employee(2, "TestName2", "TestLastName2",
                        BigDecimal.valueOf(50000L), 1),
                new Employee(3, "TestName3", "TestLastName3",
                        BigDecimal.valueOf(30000L), 1));
    }
    private List<Employee> createTestEmployeeListWithoutSubordinates() {
        return List.of(new Employee(1, "TestName", "TestLastName",
                        BigDecimal.valueOf(10000L)),
                new Employee(2, "TestName2", "TestLastName2",
                        BigDecimal.valueOf(50000L), 12),
                new Employee(3, "TestName3", "TestLastName3",
                        BigDecimal.valueOf(30000L), 13));
    }
}
