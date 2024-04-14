package org.example.service.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.example.dto.Employee;
import org.example.exception.DataValidationException;

public class CsvReaderServiceImpl implements CsvReaderService {

    private static final String DATA_EXCEPTION_MESSAGE = "Data in the CSV file is not valid";
    private final Set<Integer> setOfEmployeesUniqueIDs = new HashSet<>();

    @Override
    public List<Employee> readCsv(String fileName) {
        List<Employee> employees = new ArrayList<>();
        URL url = getClass().getResource("/" + fileName);
        if (url != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader((url).getFile()))) {
                String lineToReadIn;
                bufferedReader.readLine();
                while ((lineToReadIn = bufferedReader.readLine()) != null) {
                    String[] stringsWithData = lineToReadIn.split(",");
                    if (validateCountOfColumns(stringsWithData)) {
                        initializeEmployeeAndAddToList(stringsWithData, employees);
                    } else {
                        throw new DataValidationException(DATA_EXCEPTION_MESSAGE + " wrong number of columns:" +
                                " less than 4 or more than 5.");
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Exception in method readCsv(): " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Exception in method readCsv(): " + e.getMessage());
            }
            return employees;
        }
        return Collections.emptyList();
    }

    private void initializeEmployeeAndAddToList(String[] arrayWithData, List<Employee> employees) {
        Employee employee;
        if (arrayWithData.length > 4) {
            employee = new Employee(Integer.parseInt(arrayWithData[0]), arrayWithData[1],
                    arrayWithData[2],
                    BigDecimal.valueOf(Long.parseLong(arrayWithData[3])),
                    Integer.parseInt(arrayWithData[4]));
            validateEmployeeId(employee,employees);
        } else {
            employee = new Employee(Integer.parseInt(arrayWithData[0]), arrayWithData[1],
                    arrayWithData[2],
                    BigDecimal.valueOf(Long.parseLong(arrayWithData[3])));
            validateEmployeeId(employee,employees);
        }
    }
    private void validateEmployeeId(Employee employee, List<Employee> employees) {
        if (!isDuplicate(employee)) {
            employees.add(employee);
        }else {
            throw new DataValidationException(DATA_EXCEPTION_MESSAGE + " duplicates were found: " + employee);
        }
    }
    private boolean isDuplicate(Employee employee) {
        if (setOfEmployeesUniqueIDs.contains(employee.getId())) {
            return true;
        }else {
            setOfEmployeesUniqueIDs.add(employee.getId());
            return false;
        }
    }

    private boolean validateCountOfColumns(String[] stringsWithData) {
        return stringsWithData != null && stringsWithData.length >= 4
                && stringsWithData.length <= 5;
    }
}
