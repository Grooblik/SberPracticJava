package src;
import java.util.List;
import java.util.regex.Pattern;

public class City {

    public static final String CITY_NAME_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- ]+";
    public static final String CITY_REGION_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- .]+";

    public static final String CITY_DISTRICT_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- ]+";

    private String name;
    private String region;
    private String district;
    private int population;

    private String foundation;

    public City(
            String name,
            String region,
            String district,
            int population,
            String foundation
    ) throws IllegalArgumentException
    {
        setName(name);
        setRegion(region);
        setDistrict(district);
        setPopulation(population);
        setFoundation(foundation);
    }

    public static City recordToCity(List<String> record) {
        String name, region, district, populationStr, foundation;

        if (record.size() < 5) throw new IllegalArgumentException("Incorrect record input! (id=" + record.get(0) + ")");

        name = record.get(1);
        region = record.get(2);
        district = record.get(3);
        populationStr = record.get(4);
        foundation = record.size() < 6 ? null : record.get(5);


        int population = getPopulationFromString(populationStr);

        try {
            return new City(name, region, district, population, foundation);
        } catch (IllegalArgumentException e) { // got incorrect record
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    private static int getPopulationFromString(String populationStr) {
        int population;
        try {
            population = Integer.parseInt(populationStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Population isn't an integer!", e);
        }
        return population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        boolean correctName = Pattern.matches(CITY_NAME_REGEX, name);
        if (!correctName) throw new IllegalArgumentException("Incorrect city name!");
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) throws IllegalArgumentException {
        boolean correctRegion = Pattern.matches(CITY_REGION_REGEX, region);
        if (!correctRegion) throw new IllegalArgumentException("Incorrect city region!");
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) throws IllegalArgumentException {
        boolean correctDistrict = Pattern.matches(CITY_DISTRICT_REGEX, district);
        if (!correctDistrict) throw new IllegalArgumentException("Incorrect city district!");
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) throws IllegalArgumentException {
        boolean correctPopulation = population > 0;
        if (!correctPopulation) throw new IllegalArgumentException("Incorrect city population!");
        this.population = population;
    }

    public String getFoundation() {
        if (foundation == null)
            return "н/у";
        return foundation;
    }

    public void setFoundation(String foundation) throws IllegalArgumentException {
        if (foundation == null) {
            this.foundation = null;
            return;
        }
        boolean correctFoundation = !foundation.isEmpty();
        if (!correctFoundation) throw new IllegalArgumentException("Incorrect foundation!");
        this.foundation = foundation;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "name='" + getName() + '\'' +
                ", region='" + getRegion() + '\'' +
                ", district='" + getDistrict() + '\'' +
                ", population=" + getPopulation() +
                ", foundation='" + getFoundation() + '\'' +
                '}' + '\n';
    }
}
