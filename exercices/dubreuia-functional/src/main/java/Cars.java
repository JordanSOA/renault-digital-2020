import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Cars {

    public static void main(String[] args) throws IOException {
        // TODO change path to cars.csv
        String path = "C:/Users/Workspace/Desktop/Coaching/Nouveau dossier/renault-digital-2020/exercices/dubreuia-functional/data/cars.csv";

        Stream<String> lines = Files.lines(Paths.get(path), UTF_8);
        List<Car> cars = getCars(lines);
        System.out.println("cars.size: " + cars.size());

       // Map<Integer, List<Car>> perYear = getCarsPerYear(cars);
       // System.out.println("perYear: " + perYear);

        Map<Integer, Long> carsCountPerYear = getCarsCountPerYear(cars);
        System.out.println("carsCountPerYear: " + carsCountPerYear);
    }

    private static List<Car> getCars(Stream<String> lines) {
        List<Car> carsList = new ArrayList<>();
        lines.skip(1).forEach(line -> {
            String[] split = line.split(",");
            carsList.add(new Car(Integer.parseInt(split[0]), split[1], split[2]));
        });
        return carsList;
    }

    private static Map<Integer, List<Car>> getCarsPerYear(List<Car> cars) {

        Map<Integer, List<Car>> collect = cars.stream()
            .collect(Collectors.groupingBy(car -> car.year));
        return collect;
    }

    private static Map<Integer, Long> getCarsCountPerYear(List<Car> cars) {
        Map<Integer, Long> map = new HashMap<>();
        int size = cars.stream()
            .collect(Collectors.groupingBy());
        System.out.println(size);
        return Collections.emptyMap();
    }

    private static class Car {

        private final int year;
        private final String brand;
        private final String model;

        public Car(int year, String brand, String model) {
            this.year = year;
            this.brand = brand;
            this.model = model;
        }

        public int getYear() {
            return year;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "year=" + year +
                    ", brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }

    }

}
