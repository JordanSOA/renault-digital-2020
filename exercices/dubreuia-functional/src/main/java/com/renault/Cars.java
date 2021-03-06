package com.renault;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Cars {

    public static final int DEFAULT_MPG = 100;
    
    /*
     * Retourne une liste de voiture à partir d'une liste d'un stream de lignes.
     *
     * Utilise la méthode Car.build.
     */
    public static List<Car> getCars(Stream<String> lines) {
        return lines.skip(1)
            .map(line -> line.split(","))
            .map(split -> Car.build(split[0], split[1], split[2], split[3]))
            .collect(Collectors.toList());
    }

    /*
     * Retourne la voiture qui correspond à ces critères.
     */
    public static Optional<Car> getCar(List<Car> cars, int year, String brand, String model) {
        return cars.stream()
            .filter(car -> car.getYear() == year && brand.equals(car.brand) && model.equals(car.model)).findFirst();
    }

    /*
     * Retourne la consommation en MPG de la voiture, ou la valeur de DEFAULT_MPG si le MPG n'existe pas.
     */
    public static Integer getMpgOrDefault(Car car) {
        return car.getMpg().orElse(DEFAULT_MPG);
    }

    /*
     * Retourne la consommation unité métrique, pour le calcul,
     * voir https://www.calculateme.com/gas-mileage/us-mpg-to-liters-per-100-km
     */
    public static Optional<Double> getLitersPer100Km(Car car) {
        return car.getMpg().map(mpg -> (100 * 3.785411784) / (1.609344 * mpg));
    }

    /*
     * Retourne la somme de tous les MPG pour une marque en particulier.
     */
    public static Optional<Integer> sumMpgForBrand(List<Car> cars, String brand) {
        Stream<Car> stream = cars.stream().filter(car -> brand.equalsIgnoreCase(car.brand));
        Optional<Integer> reduce = stream.map(car -> car.getMpg())
            .map(mpg -> mpg.orElse(0))
            .reduce((acc, cur) -> acc + cur);
        return reduce;
    }

    /*
     * Retourne vrai si ce modèle existe.
     */
    public static boolean containsModelIgnoreCase(List<Car> cars, String model) {
        return cars.stream().anyMatch(car -> car.model.equalsIgnoreCase(model));
    }

    /*
     * Retourne une map avec comme clefs les années, comme valeur la liste des voitures pour l'année correspondante.
     */
    public static Map<Integer, List<Car>> getCarsPerYear(List<Car> cars) {
        return cars.stream().collect(Collectors.groupingBy(Car::getYear));
    }

    /*
     * Retourne une map avec comme clefs les années, comme valeur le compte des voitures pour cette année.
     */
    public static Map<Integer, Long> getCarsCountPerYear(List<Car> cars) {
        return cars.stream().collect(Collectors.groupingBy(Car::getYear, Collectors.counting()));
    }

    /**
     * La classe voiture, ne pas toucher :)
     */
    public static class Car {

        private final int year;
        private final String brand;
        private final String model;
        private final Integer mpg;

        public static Car build(String year, String brand, String model, String mpg) {
            int yearMapped = Integer.parseInt(year);
            Integer mpgMapped;
            try {
                mpgMapped = Integer.parseInt(mpg);
            } catch (NumberFormatException e) {
                mpgMapped = null;
            }
            String brandMapped = brand.replaceAll("\"", "");
            String modelMapped = model.replaceAll("\"", "");
            return new Car(yearMapped, brandMapped, modelMapped, mpgMapped);
        }

        public Car(int year, String brand, String model, Integer mpg) {
            this.year = year;
            this.brand = brand;
            this.model = model;
            this.mpg = mpg;
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

        public Optional<Integer> getMpg() {
            return Optional.ofNullable(mpg);
        }

        @Override
        public String toString() {
            return "Car{" +
                    "year=" + year +
                    ", brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    ", mpg=" + mpg +
                    '}';
        }

    }

}
