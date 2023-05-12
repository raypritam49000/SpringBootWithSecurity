package com.user.management.system.utility;

import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.user.management.system.dto.UserDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CvsFileWriter {

    public static ByteArrayInputStream userToCSV(List<UserDTO> users) {

        final CSVFormat format = CSVFormat.DEFAULT.withHeader("ID", "First Name", "Last Name", "Email Id", "Password");

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            for (UserDTO user : users) {
                List<String> data = Arrays.asList(user.getId(), user.getFirstName(), user.getLastName(), user.getEmailId(), user.getPassword());
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

    public static void writeDataCsv(List<UserDTO> data) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final String CSV_LOCATION = "UsersList" + System.currentTimeMillis() + ".csv";

        // Creating writer class to generate
        // csv file
        FileWriter writer = new FileWriter(Paths.get(CSV_LOCATION).toFile());

        // Create Mapping Strategy to arrange the
        // column name in order
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(UserDTO.class);

        //mappingStrategy.generateHeader(new UserDTO());

        String[] columns = new String[]{"id", "firstName", "lastName", "emailId", "password"};
        mappingStrategy.setColumnMapping(columns);

        // Creating StatefulBeanToCsv object
        StatefulBeanToCsvBuilder<UserDTO> builder = new StatefulBeanToCsvBuilder(writer);
        StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();

        // Write list to StatefulBeanToCsv object
        beanWriter.write(data);

        // closing the writer object
        writer.close();
    }

    public static List<String[]> readCsvToBeanList(String fileName) throws Exception {
        //Build reader instance
        CSVReader reader = new CSVReader(new FileReader(new File(fileName)));

        //Read all rows at once
        List<String[]> allRows = reader.readAll();

        for (String[] row : allRows) {
            System.out.println(Arrays.toString(row));
        }

        return allRows;
    }

    public static void writeUsersToCsv(Writer writer, List<UserDTO> users) {

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("ID","FIRSTNAME","LASTNAME","EMAILID","PASSWORD");
            for (UserDTO user : users) {
                csvPrinter.printRecord(user.getId(), user.getFirstName(), user.getLastName(), user.getEmailId(), user.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
