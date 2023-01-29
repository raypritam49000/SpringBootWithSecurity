package com.user.management.system.controllers;

import java.io.*;
import java.util.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.user.management.system.utility.CvsFileWriter;
import com.user.management.system.utility.ExcelFileWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.management.system.dto.UserDTO;
import com.user.management.system.service.UserService;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public UserDTO saveUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @GetMapping("/")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable("id") String id) {
        return Map.of("deleted", userService.deleteUserById(id));
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @GetMapping("/excel")
    @ResponseStatus(HttpStatus.OK)
    public void userDataExcelFileWriter() {
        List<UserDTO> userDTOS = userService.getUsers();
        String fileName = "user" + new Date().toString() + ".csv";
        ExcelFileWriter.writeToExcel(fileName, userDTOS);
    }

    @GetMapping("/export-users")
    public ResponseEntity<InputStreamResource> exportUserDateCSV(HttpServletResponse response) throws Exception {
        String filename = "user" + System.currentTimeMillis() + ".csv";
        InputStreamResource file = new InputStreamResource(userService.load());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename).contentType(MediaType.parseMediaType("application/csv")).body(file);
    }

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        String filename = "Employee-List" + System.currentTimeMillis() + ".csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<UserDTO> writer =
                new StatefulBeanToCsvBuilder<UserDTO>(response.getWriter())
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        // write all employees to csv file
        writer.write(userService.getUsers());

    }


    @GetMapping("/exportUser")
    public void exportUserCSV(HttpServletResponse response) throws Exception {
        String filename = "Employee-List" + System.currentTimeMillis() + ".csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        CvsFileWriter.writeDataCsv(userService.getUsers());
    }

    @RequestMapping(path = "/export/users")
    public void getAllUserInCsv(HttpServletResponse servletResponse) throws IOException {
        String filename = "employees" + System.currentTimeMillis() + ".csv";
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        CvsFileWriter.writeUsersToCsv(servletResponse.getWriter(), userService.getUsers());
    }

    @GetMapping("/users/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");

        String filename = "employees" + System.currentTimeMillis() + ".csv";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + filename + ".csv";

        response.setHeader(headerKey, headerValue);
        List<UserDTO> listUsers = userService.getUsers();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "FULL NAME", "LAST NAME", "EMAIL", "PASSWORD"};
        String[] nameMapping = {"id", "firstName", "lastName", "emailId", "password"};

        csvWriter.writeHeader(csvHeader);

        for (UserDTO user : listUsers) {
            csvWriter.write(user, nameMapping);
        }
        csvWriter.close();
    }


    @GetMapping("/excel/export")
    public void exportExcel(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Participant");

        List<UserDTO> listUsers = userService.getUsers();

        int indiceMap = 2;
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"id", "first name", "last name", "email", "password"});

        for (UserDTO user : listUsers) {
            data.put(Integer.toString(indiceMap), new Object[]{user.getId(), user.getFirstName(), user.getLastName(), user.getEmailId(), user.getPassword()});
            indiceMap++;
        }

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }

        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        workbook.write(outByteStream);

        byte[] outArray = outByteStream.toByteArray();

        String fileName = "User-List" + System.currentTimeMillis() + ".xlsx";

        response.setContentType("application/vnd.ms-excel");
        response.setContentLength(outArray.length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);

        outStream.flush();
        outStream.close();
    }

}
