package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SEMICOLON_DELIMITER = ";";

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(SEMICOLON_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
    public static void main(String[] args) {

        List<List<String>> records = getRecordsFromCSVFile("../cities.csv");


    }

    private static List<List<String>> getRecordsFromCSVFile(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                 records.add(
                     getRecordFromLine(scanner.nextLine())
                 );
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        return records;
    }
}
