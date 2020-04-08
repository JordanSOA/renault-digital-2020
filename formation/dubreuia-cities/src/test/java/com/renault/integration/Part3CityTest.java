package com.renault.integration;

import com.renault.CitiesApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CitiesApplication.class, webEnvironment = RANDOM_PORT)
public class Part3CityTest extends TestCitiesApplication {

    @Test
    public void should_POST_region_add_new_region() {
        JsonObject ukMidwest = Json.createObjectBuilder()
                .add("countryName", "United Kingdom")
                .add("countryLanguage", "English")
                .add("regionName", "Midwest")
                .add("cityName", "London")
                .build();
        post("country/region/createCountryRegionCity", ukMidwest);

        List<String> countryNames = getCountryNames();
        assertEquals(4, countryNames.size());
        assertTrue(countryNames.contains("United Kingdom"));

        List<String> regionNames = getRegionNames();
        assertEquals(3, regionNames.size());
        assertTrue(regionNames.contains("Midwest"));

        List<String> cityNames = getCityNames();
        assertEquals(5, cityNames.size());
        assertTrue(cityNames.contains("London"));
    }

    @Test
    public void should_PUT_city_modify_existing_city() {
        int montrealId = getCityIdForName("Montréal").orElseThrow();
        JsonObject updatedMontrel = Json.createObjectBuilder()
                .add("id", montrealId)
                .add("name", "Super Montréal")
                .build();
        put(format("country/region/city/%s", montrealId), updatedMontrel);

        List<String> cityNames = getCityNames();
        assertEquals(4, cityNames.size());
        assertTrue(cityNames.contains("Super Montréal"));
    }

    @Test
    public void should_DELETE_city_should_remove_city_even_if_followed() {
        int parisId = getCityIdForName("Paris").orElseThrow();
        delete(format("country/region/city/%s", parisId));

        List<String> cityNames = getCityNames();
        assertEquals(3, cityNames.size());
        assertFalse(cityNames.contains("Paris"));

        List<String> userNames = getUserNames();
        assertEquals(1, userNames.size());
        assertTrue(userNames.contains("Alex"));
    }
}
