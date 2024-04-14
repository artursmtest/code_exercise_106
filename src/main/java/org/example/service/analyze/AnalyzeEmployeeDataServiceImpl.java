package org.example.service.analyze;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.example.dto.Employee;

public class AnalyzeEmployeeDataServiceImpl implements AnalyzeEmployeeDataService {

    private static final String EMPTY_OR_NULL_LIST_MESSAGE =
            "The list of employees is empty or null. No further processing is possible.";

    private static final int MAX_ALLOWED_RELATIONS_BETWEEN_MANAGERS_EXCLUDING_MANAGER_AND_CEO = 4;

    private static final int MAX_RELATIONS_INCLUDING_MANAGER_AND_CEO = 6;

    private static final String WARNING_EARNS_LESS_MESSAGE_TEMPLATE =
            "Manager %s %s earns %s less than 20%% above the average salary of their subordinates.";

    private static final String WARNING_EARNS_MORE_MESSAGE_TEMPLATE =
            "Manager %s %s earns %s more than 50%% above the average salary of their subordinates.";

    private static final String WARNING_LONG_REPORTING_LINE_MESSAGE_TEMPLATE =
            "Manager %s %s has reporting line for a %s line/s longer than maximum allowed 4.";

    private static final String NEW_LINE = "%n";

    private static final BigDecimal TWENTY_PERCENT_MORE = BigDecimal.valueOf(1.2);

    private static final BigDecimal FIFTY_PERCENT_MORE = BigDecimal.valueOf(1.5);

    @Override
    public void getInfoAboutManagersWhoEarnsLessThanAverageSubordinates(List<Employee> employees) {
        if (employees != null && !employees.isEmpty()) {
            employees.stream()
                    .filter(employee -> !employee.getSubordinates().isEmpty())
                    .filter(employee -> {
                        BigDecimal twentyPercentMoreThanAverage = countAverageSubordinatesSalary(employee)
                                .multiply(TWENTY_PERCENT_MORE);

                        return employee.getSalary().compareTo(twentyPercentMoreThanAverage) < 0;
                    })
                    .map(employee ->
                            countSalaryDifference(employee, true)
                    )
                    .forEach(entry -> System.out.printf(
                            (WARNING_EARNS_LESS_MESSAGE_TEMPLATE) + NEW_LINE,
                            Objects.toString(entry.getKey().getFirstName(), ""),
                            Objects.toString(entry.getKey().getLastName(), ""),
                            Objects.toString(entry.getValue().toString(), "")));
        } else {
            System.out.println(EMPTY_OR_NULL_LIST_MESSAGE);
        }
    }

    @Override
    public void getInfoAboutManagersWhoEarnsMoreThanAverageSubordinates(List<Employee> employees) {
        if (employees != null && !employees.isEmpty()) {
            employees.stream()
                    .filter(employee -> !employee.getSubordinates().isEmpty())
                    .filter(employee -> {
                        BigDecimal minTwentyPercentMoreThanAverage = countAverageSubordinatesSalary(employee)
                                .multiply(TWENTY_PERCENT_MORE);
                        BigDecimal maxFiftyPercentMoreThanAverage = countAverageSubordinatesSalary(employee)
                                .multiply(FIFTY_PERCENT_MORE);
                        return employee.getSalary().compareTo(minTwentyPercentMoreThanAverage) >= 0
                                && employee.getSalary().compareTo(maxFiftyPercentMoreThanAverage) > 0;
                    })
                    .map(employee ->
                            countSalaryDifference(employee, false))
                    .forEach(entry -> System.out.printf(
                            (WARNING_EARNS_MORE_MESSAGE_TEMPLATE) + NEW_LINE,
                            Objects.toString(entry.getKey().getFirstName(), ""),
                            Objects.toString(entry.getKey().getLastName(), ""),
                            Objects.toString(entry.getValue().toString(), "")));
        } else {
            System.out.println(EMPTY_OR_NULL_LIST_MESSAGE);
        }
    }

    @Override
    public void getInfoAboutManagersWhoHasReportingLineLongerThan4(List<Employee> employees) {
        if (employees != null && !employees.isEmpty()) {
            employees.forEach(employee -> countManagerRelationsToCEO(employees, employee.getId()));
        } else {
            System.out.println(EMPTY_OR_NULL_LIST_MESSAGE);
        }

    }

    private BigDecimal countAverageSubordinatesSalary(Employee employee) {
        return employee.getSubordinates().stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(employee.getSubordinates().size()), 2, RoundingMode.HALF_UP);
    }

    private AbstractMap.SimpleEntry<Employee, BigDecimal> countSalaryDifference(Employee employee,
            boolean isLessSalaryNeededToCount) {
        if (isLessSalaryNeededToCount) {
            BigDecimal twentyPercentMoreThanAverage = countAverageSubordinatesSalary(employee)
                    .multiply(TWENTY_PERCENT_MORE);
            BigDecimal salaryDifference = twentyPercentMoreThanAverage.subtract(employee.getSalary());
            return new AbstractMap.SimpleEntry<>(employee, salaryDifference);
        } else {
            BigDecimal maxFiftyPercentMoreThanAverage = countAverageSubordinatesSalary(employee)
                    .multiply(FIFTY_PERCENT_MORE);
            BigDecimal salaryDifference = employee.getSalary().subtract(maxFiftyPercentMoreThanAverage);
            return new AbstractMap.SimpleEntry<>(employee, salaryDifference);
        }
    }

    private void countManagerRelationsToCEO(List<Employee> employees, int employeeId) {
        int relationsCount = 0;
        int currentEmployeeId = employeeId;
        List<Employee> list = new ArrayList<>();

        if (employees != null && !employees.isEmpty()) {
            while (currentEmployeeId != 0) {
                Employee currentEmployee = getEmployeeById(employees, currentEmployeeId);
                if (currentEmployee != null) {
                    list.add(currentEmployee);
                    currentEmployeeId = currentEmployee.getManagerId();
                    relationsCount++;
                    if (relationsCount > MAX_RELATIONS_INCLUDING_MANAGER_AND_CEO && currentEmployeeId == 0) {
                        System.out.printf((WARNING_LONG_REPORTING_LINE_MESSAGE_TEMPLATE) + NEW_LINE,
                                Objects.toString(list.get(0).getFirstName(), ""),
                                Objects.toString(list.get(0).getLastName(), ""),
                                (relationsCount - 2) - MAX_ALLOWED_RELATIONS_BETWEEN_MANAGERS_EXCLUDING_MANAGER_AND_CEO + "");
                    }
                } else {
                    break;
                }
            }
        } else {
            System.out.println(EMPTY_OR_NULL_LIST_MESSAGE);
        }

    }

    private Employee getEmployeeById(List<Employee> employees, int employeeId) {
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                return employee;
            }
        }
        return null;
    }
}

