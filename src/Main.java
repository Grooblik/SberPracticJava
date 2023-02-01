package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static final String SEMICOLON_DELIMITER = ";";
    public static final String CSV_FILE_PATH = "cities.csv";

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
        List<City> cities = getCitiesFromFile();
        if (cities == null) return;

        sortingDemonstration(cities);

        showMaxPopulation(cities);

        showRegionsCitiesCount(cities);

    }

    private static void showRegionsCitiesCount(List<City> cities) {

        System.out.println("Region's cities count:");

        cities
                .stream().map(city -> city.getRegion())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                )
                .forEach((reg, count) -> System.out.println(reg + " - " + count));
    }

    private static void showMaxPopulation(List<City> cities) {
        City[] citiesArray = cities.toArray(City[]::new);
        int max_population = -1, max_index = 0;
        for (int index = 0; index < citiesArray.length; index++) {
            if (max_population < citiesArray[index].getPopulation()) {
                max_index = index;
            }
        }

        System.out.println("Max population:");
        System.out.println("[" + max_index + "] = " + citiesArray[max_index].getPopulation());
    }


    private static void sortingDemonstration(List<City> cities) {
        System.out.println("Without sort");
        System.out.println(cities);


        System.out.println("Sorted by name:");

        Comparator<City> nameComparator = Comparator.comparing(city -> city.getName().toLowerCase());


        System.out.println("Sorted by name");
        System.out.println(
            getSortedList(cities, nameComparator)
        );

        Comparator<City> districtComparator = Comparator.comparing(City::getDistrict);
        districtComparator = districtComparator.thenComparing(City::getName);

        System.out.println("Sorted by district and name");
        System.out.println(
                getSortedList(cities, districtComparator)
        );
    }

    private static List<City> getCitiesFromFile() {
        List<List<String>> records;
        try {
            records = getRecordsFromCSVFile(CSV_FILE_PATH);
        } catch (RuntimeException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return records.stream().map(
                City::recordToCity
        ).toList();
    }

    private static List<City> getSortedList(List<City> cities, Comparator<City> nameComparator) {
        return cities.
                stream().sorted(
                        nameComparator
                ).toList();
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
