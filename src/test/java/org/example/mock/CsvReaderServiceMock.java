package org.example.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.example.dto.Employee;
import org.example.service.csv.CsvReaderService;

public class CsvReaderServiceMock implements CsvReaderService {

    @Override
    public List<Employee> readCsv(String fileName) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "John", "Doe", BigDecimal.valueOf(5000), 0));
        list.add(new Employee(2, "Jack", "Dong", BigDecimal.valueOf(3000), 1));
        list.add(new Employee(3, "Bob", "Chack", BigDecimal.valueOf(4000), 1));
        return list;
    }
}
