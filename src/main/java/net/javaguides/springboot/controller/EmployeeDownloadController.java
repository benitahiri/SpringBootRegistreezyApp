package net.javaguides.springboot.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeDownloadController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadEmployees() throws IOException {
        List<Employee> employees = employeeRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Emri");
        headerRow.createCell(2).setCellValue("Mbiemri");
        headerRow.createCell(3).setCellValue("Numri i Protokollit");
        headerRow.createCell(4).setCellValue("Email");
        headerRow.createCell(5).setCellValue("Data e Regjistrimit");
        headerRow.createCell(6).setCellValue("Data e Lindjes");

        int rowNum = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getFirstName() != null ? employee.getFirstName() : "");
            row.createCell(2).setCellValue(employee.getLastName() != null ? employee.getLastName() : "");
            row.createCell(3).setCellValue(employee.getProtocolNumber() != null ? employee.getProtocolNumber() : "");
            row.createCell(4).setCellValue(employee.getEmail() != null ? employee.getEmail() : "");
            row.createCell(5).setCellValue(employee.getRegistrationDate() != null ? employee.getRegistrationDate().toString() : "");
            row.createCell(6).setCellValue(employee.getBirthdate() != null ? employee.getBirthdate().toString() : "");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/download/{startDate}/{endDate}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadEmployeesByRegistrationDateRange(
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate) throws IOException {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Employee> employees = employeeRepository.findByRegistrationDateBetween(start, end);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Emri");
        headerRow.createCell(2).setCellValue("Mbiemri");
        headerRow.createCell(3).setCellValue("Numri i Protokollit");
        headerRow.createCell(4).setCellValue("Email");
        headerRow.createCell(5).setCellValue("Data e Regjistrimit");
        headerRow.createCell(6).setCellValue("Data e Lindjes");

        int rowNum = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getFirstName() != null ? employee.getFirstName() : "");
            row.createCell(2).setCellValue(employee.getLastName() != null ? employee.getLastName() : "");
            row.createCell(3).setCellValue(employee.getProtocolNumber() != null ? employee.getProtocolNumber() : "");
            row.createCell(4).setCellValue(employee.getEmail() != null ? employee.getEmail() : "");
            row.createCell(5).setCellValue(employee.getRegistrationDate() != null ? employee.getRegistrationDate().toString() : "");
            row.createCell(6).setCellValue(employee.getBirthdate() != null ? employee.getBirthdate().toString() : "");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream.toByteArray());
    }
}
