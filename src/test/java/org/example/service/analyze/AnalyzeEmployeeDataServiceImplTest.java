package org.example.service.analyze;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.example.dto.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnalyzeEmployeeDataServiceImplTest {

    private AnalyzeEmployeeDataService analyzeEmployeeDataService;

    private PrintStream originalOutput;
    private ByteArrayOutputStream baos;

    @Before
    public void setUp() {
        analyzeEmployeeDataService = new AnalyzeEmployeeDataServiceImpl();
        originalOutput = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }
    @After
    public void tearDown() {
        System.setOut(originalOutput);
    }

    @Test
    public void getInfoAboutManagersWhoEarnsLessThanAverageSubordinates() {
        List<Employee> employeeList = createTestEmployeeListWithManagerWhoEarnsLess();
        employeeList.get(0).setSubordinates(List.of(employeeList.get(1), employeeList.get(2)));

        analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(employeeList);

        String expected = "Manager TestName TestLastName earns 38000.000 less than 20% above the average salary of their" +
                " subordinates.\r\n";
        assertEquals(expected, baos.toString());

    }

    @Test
    public void getInfoAboutManagersWhoEarnsLessThanAverageSubordinatesNull() {
        analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(null);

        String expected = "The list of employees is empty or null. No further processing is possible." +
                "\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void getInfoAboutManagersWhoEarnsLessThanAverageSubordinatesEmpty() {
        analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(new ArrayList<>());

        String expected = "The list of employees is empty or null. No further processing is possible." +
                "\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates() {
        List<Employee> employeeList = createTestEmployeeListWithManagerWhoEarnsMore();
        employeeList.get(0).setSubordinates(List.of(employeeList.get(1), employeeList.get(2)));

        analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(employeeList);

        String expected = "Manager TestName TestLastName earns 940000.000 more than 50% above the average salary of their" +
                " subordinates.\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void getInfoAboutManagersWhoEarnsMoreThanAverageSubordinatesNull() {
        analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(null);

        String expected = "The list of employees is empty or null. No further processing is possible.\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void getInfoAboutManagersWhoEarnsMoreThanAverageSubordinatesEmpty() {
        analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(new ArrayList<>());

        String expected = "The list of employees is empty or null. No further processing is possible.\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void testGetInfoAboutManagersWhoHasReportingLineLongerThan4() {
        List<Employee> employeeList = createTestEmployeeListWithTooLongReportingLine();

        analyzeEmployeeDataService.getInfoAboutManagersWhoHasReportingLineLongerThan4(employeeList);

        String expected = "Manager TestName6 TestLastName6 has reporting line for a 1 line/s longer" +
                " than maximum allowed 4.\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void testGetInfoAboutManagersWhoHasReportingLineLongerThan4Null() {
        analyzeEmployeeDataService.getInfoAboutManagersWhoHasReportingLineLongerThan4(null);

        String expected = "The list of employees is empty or null. No further processing is possible.\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void testGetInfoAboutManagersWhoHasReportingLineLongerThan4Empty() {
        analyzeEmployeeDataService.getInfoAboutManagersWhoHasReportingLineLongerThan4(new ArrayList<>());

        String expected = "The list of employees is empty or null. No further processing is possible.\r\n";
        assertEquals(expected, baos.toString());
    }

    private List<Employee> createTestEmployeeListWithTooLongReportingLine() {
        return List.of(new Employee(1, "TestName", "TestLastName",
                        BigDecimal.valueOf(1000000L), 0),
                new Employee(2, "TestName2", "TestLastName2",
                        BigDecimal.valueOf(50000L), 1),
                new Employee(3, "TestName3", "TestLastName3",
                        BigDecimal.valueOf(30000L), 2),
                new Employee(4, "TestName4", "TestLastName4",
                        BigDecimal.valueOf(150000L), 3),
                new Employee(5, "TestName5", "TestLastName5",
                        BigDecimal.valueOf(56000L), 4),
                new Employee(6, "TestName6", "TestLastName6",
                        BigDecimal.valueOf(37800L), 5),
                new Employee(7, "TestName6", "TestLastName6",
                        BigDecimal.valueOf(35600L), 6));
    }
    private List<Employee> createTestEmployeeListWithManagerWhoEarnsLess() {
        return List.of(new Employee(1, "TestName", "TestLastName",
                        BigDecimal.valueOf(10000L)),
                new Employee(2, "TestName2", "TestLastName2",
                        BigDecimal.valueOf(50000L), 1),
                new Employee(3, "TestName3", "TestLastName3",
                        BigDecimal.valueOf(30000L), 1));
    }
    private List<Employee> createTestEmployeeListWithManagerWhoEarnsMore() {
        return List.of(new Employee(1, "TestName", "TestLastName",
                        BigDecimal.valueOf(1000000L)),
                new Employee(2, "TestName2", "TestLastName2",
                        BigDecimal.valueOf(50000L), 1),
                new Employee(3, "TestName3", "TestLastName3",
                        BigDecimal.valueOf(30000L), 1));
    }
}
