package org.example.service.report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.dto.Employee;
import org.example.mock.AnalyzeEmployeeDataServiceMock;
import org.example.mock.CsvReaderServiceMock;
import org.example.mock.EmployeeServiceMock;
import org.example.service.analyze.AnalyzeEmployeeDataService;
import org.example.service.csv.CsvReaderService;
import org.example.service.employee.EmployeeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReportingServiceImplTest {

    private ReportingService reportingService;

    private ByteArrayOutputStream baos;

    private PrintStream originalOut;

    @Before
    public void setUp() {
        AnalyzeEmployeeDataService mockAnalyzeEmployeeDataService = new AnalyzeEmployeeDataServiceMock();
        CsvReaderService mockCsvReaderService = new CsvReaderServiceMock();
        EmployeeService mockEmployeeService = new EmployeeServiceMock();
        reportingService = new ReportingServiceImpl(mockAnalyzeEmployeeDataService, mockCsvReaderService,
                mockEmployeeService);
        originalOut = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }
    @After
    public void tearDown() {
        System.setOut(originalOut);
    }


    @Test
    public void testGenerateReportFromEmployeeDataWithData() {
        reportingService.generateReportFromEmployeeData();

        String expected = "Manager John Doe earns less than 20% above the average salary of their subordinates.\r\n" +
                "Manager Tom Soer earns more than 50% above the average salary of their subordinates.\r\n" +
                "Manager Bob Rich has more than 4 reporting line.\r\n";
        assertEquals(expected, baos.toString());
    }

    @Test
    public void testGenerateReportFromEmployeeDataWithNoData() {
        EmployeeServiceMock.emptyList = true;

        reportingService.generateReportFromEmployeeData();

        String expected = "There is no data available to generate the report\r\n";
        assertEquals(expected, baos.toString());
    }
}
