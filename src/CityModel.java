package src;
import java.util.List;
import java.util.regex.Pattern;

public class CityModel {

    public static final String CITY_NAME_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- ]+";
    public static final String CITY_REGION_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- .]+";

    public static final String CITY_DISTRICT_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- ]+";

    private String name;
    private String region;
    private String district;
    private int population;

    private String foundation;

    public CityModel(
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

    public static CityModel recordToCity(List<String> record) {
        String name, region, district, populationStr, foundation;
        try {
            name = record.get(1);
            region = record.get(2);
            district = record.get(3);
            populationStr = record.get(4);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Incorrect record input! (id=" + record.get(0) + ")", e);
        }
        try {
            foundation = record.get(5);
        } catch (IndexOutOfBoundsException e) {
            foundation = null; // No foundation info
        }
        int population;

        try {
            population = Integer.parseInt(populationStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Population isn't an integer!", e);
        }
        try {
            return new CityModel(name, region, district, population, foundation);
        } catch (IllegalArgumentException e) { // got incorrect record
            System.out.println(e.getLocalizedMessage());
            return null;
        }
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
                '}';
    }
}
