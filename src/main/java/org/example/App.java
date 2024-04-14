package org.example;

import org.example.service.analyze.AnalyzeEmployeeDataService;
import org.example.service.analyze.AnalyzeEmployeeDataServiceImpl;
import org.example.service.csv.CsvReaderService;
import org.example.service.csv.CsvReaderServiceImpl;
import org.example.service.employee.EmployeeService;
import org.example.service.employee.EmployeeServiceImpl;
import org.example.service.report.ReportingService;
import org.example.service.report.ReportingServiceImpl;

/**
 * My App runner.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CsvReaderService csvReaderService = new CsvReaderServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        AnalyzeEmployeeDataService analyzeEmployeeDataService = new AnalyzeEmployeeDataServiceImpl();
        ReportingService reportingService = new ReportingServiceImpl(analyzeEmployeeDataService,csvReaderService,
                employeeService);
        reportingService.generateReportFromEmployeeData();

    }
}
