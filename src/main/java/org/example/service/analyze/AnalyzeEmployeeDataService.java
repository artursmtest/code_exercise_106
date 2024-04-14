package org.example.service.analyze;

import java.util.List;

import org.example.dto.Employee;

public interface AnalyzeEmployeeDataService {

    void getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(List<Employee> employees);

    void getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(List<Employee> employees);

    void getInfoAboutManagersWhoHasReportingLineLongerThan4(List<Employee> employees);
}
