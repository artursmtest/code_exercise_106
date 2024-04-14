package org.example.service.csv;

import java.math.BigDecimal;
import java.util.List;

import org.example.dto.Employee;
import org.example.exception.DataValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CsvReaderServiceImplTest {

    private CsvReaderService csvReaderService;

    @Before
    public void setUp() {
        csvReaderService = new CsvReaderServiceImpl();
    }

    @Test
    public void testReadCsv() {
        List<Employee> result = csvReaderService.readCsv("employees-test.csv");

        assertThat(result.size(), equalTo(6));
        assertThat(result.get(5).getFirstName(), equalTo("Tom"));
        assertThat(result.get(5).getLastName(), equalTo("Soer"));
        assertThat(result.get(5).getSalary(), equalTo(BigDecimal.valueOf(50000L)));
        assertThat(result.get(5).getManagerId(), equalTo(305));
    }

    @Test
    public void testReadEmptyFile() {
        List<Employee> result = csvReaderService.readCsv("employees-empty.csv");

        assertTrue(result.isEmpty());
    }
    @Test
    public void testReadNullFile() {
        List<Employee> result = csvReaderService.readCsv(null);

        assertTrue(result.isEmpty());
    }
    @Test
    public void testReadCsvWrongDataLessCountOfColumns() {
        assertThrows(DataValidationException.class, () -> csvReaderService.readCsv(
                "employees-wrong-data-less-columns.csv"));
    }
    @Test
    public void testReadCsvWrongDataMoreCountOfColumns() {
        assertThrows(DataValidationException.class, () -> csvReaderService.readCsv(
                "employees-wrong-data-more-columns.csv"));
    }

    @Test
    public void testReadCsvFileWithDuplicates() {
        assertThrows(DataValidationException.class, () -> csvReaderService
                .readCsv("employees-with-duplicates.csv"));

    }
}
