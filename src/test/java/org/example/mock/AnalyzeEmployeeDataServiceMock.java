package org.example.mock;

import java.util.List;

import org.example.dto.Employee;
import org.example.service.analyze.AnalyzeEmployeeDataService;

public class AnalyzeEmployeeDataServiceMock implements AnalyzeEmployeeDataService {

    private static final String EMPTY_LIST_MESSAGE = "There is no data available to generate the report";
    public static boolean emptyList = false;

    @Override
    public void getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(List<Employee> employees) {
        if (emptyList) {
            System.out.println(EMPTY_LIST_MESSAGE);
        } else {
            System.out.println(String.join(" ",
                    "Manager",
                    "John",
                    "Doe",
                    "earns less than 20% above the average salary of their subordinates."));
        }
    }

    @Override
    public void getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(List<Employee> employees) {
        if (emptyList) {
            System.out.println(EMPTY_LIST_MESSAGE);
        } else {
            System.out.println(String.join(" ",
                    "Manager",
                    "Tom",
                    "Soer",
                    "earns more than 50% above the average salary of their subordinates."));
        }

    }

    @Override
    public void getInfoAboutManagersWhoHasReportingLineLongerThan4(List<Employee> employees) {
        if (emptyList) {
            System.out.println(EMPTY_LIST_MESSAGE);
        } else {
            System.out.println(String.join(" ",
                    "Manager",
                    "Bob",
                    "Rich",
                    "has more than 4 reporting line."));
        }
    }
}
