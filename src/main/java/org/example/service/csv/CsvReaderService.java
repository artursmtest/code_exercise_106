package org.example.service.csv;

import java.util.List;

import org.example.dto.Employee;

public interface CsvReaderService {

    List<Employee> readCsv(String fileName);

}
