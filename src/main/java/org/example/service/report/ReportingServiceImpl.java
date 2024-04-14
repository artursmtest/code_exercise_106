package org.example.service.report;

import java.util.List;

import org.example.dto.Employee;
import org.example.service.analyze.AnalyzeEmployeeDataService;
import org.example.service.csv.CsvReaderService;
import org.example.service.employee.EmployeeService;

public class ReportingServiceImpl implements ReportingService {

    private final AnalyzeEmployeeDataService analyzeEmployeeDataService;

    private final CsvReaderService csvReaderService;

    private final EmployeeService employeeService;

    private final String CSV_FILE_NAME = "employees.csv";

    public ReportingServiceImpl(AnalyzeEmployeeDataService analyzeEmployeeDataService,
            CsvReaderService csvReaderService, EmployeeService employeeService) {
        this.analyzeEmployeeDataService = analyzeEmployeeDataService;
        this.csvReaderService = csvReaderService;
        this.employeeService = employeeService;
    }

    @Override
    public void generateReportFromEmployeeData() {
        List<Employee> employees = employeeService.linkSubordinatesToManager(csvReaderService.readCsv(CSV_FILE_NAME));

        if (employees != null && !employees.isEmpty()) {
            analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(employees);
            analyzeEmployeeDataService.getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(employees);
            analyzeEmployeeDataService.getInfoAboutManagersWhoHasReportingLineLongerThan4(employees);
        } else {
            System.out.println("There is no data available to generate the report");
        }
    }
}
