package com.renault.integration;

import com.renault.CitiesApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.json.Json;
import javax.json.JsonObject;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CitiesApplication.class, webEnvironment = RANDOM_PORT)
public class Part5ValidTest extends TestCitiesApplication{


    // COUNTRY TEST
    // CREATE COUNTRY FAILS NAME TOO SHORT
    @Test
    public void should_POST_root_country_name_too_short_validation_fails() {
        JsonObject badCountryName = Json.createObjectBuilder()
            .add("name", "ff")
            .add("language", "English")
            .build();
        HttpStatus status = assertThrows(ResponseStatusException.class, () -> post("country/", badCountryName))
            .getStatus();
        assertEquals(BAD_REQUEST, status);

        List<String> countryNames = getCountryNames();
        assertEquals(3, countryNames.size());
    }

    // REGION TEST
    // CREATE REGION FAILS NAME TOO SHORT
    @Test
    public void should_POST_root_add_new_region_too_short_validation_fails() {
        int franceId = getCountryIdForName("France").orElseThrow();
        JsonObject badRegionName = Json.createObjectBuilder().add("name", "As").build();
        HttpStatus status = assertThrows(ResponseStatusException.class, () -> post(format("country/%s", franceId), badRegionName)).getStatus();
        assertEquals(BAD_REQUEST, status);

        List<String> regionNames = getRegionNames();
        assertEquals(2, regionNames.size());
        assertFalse(regionNames.contains("Alpes"));
    }

    // USER TEST

    // CREATE USER FAILS NAME TOO SHORT
    @Test
    public void should_Post_root_add_new_user_too_short_validation_fails(){
        JsonObject badUserName = Json.createObjectBuilder().add("name","Ol").add("citiesNames", List.of("Paris","Evry").toString()).build();
        HttpStatus status = assertThrows(ResponseStatusException.class, () -> post("user/", badUserName)).getStatus();
        assertEquals(BAD_REQUEST, status);
        List<String> userNames = getUserNames();
        assertEquals(1, userNames.size());
        assertFalse(userNames.contains("Ol"));
    }

    //CITY TEST

    //CREATE CITY FAILS , NAME TOO SHORT
    @Test
    public void should_Post_root_add_new_city_too_short_validation_fails(){
        JsonObject badCityName = Json.createObjectBuilder().add("id",328).add("name","co").build();
        ///country/region/city
        HttpStatus status = assertThrows(ResponseStatusException.class, () -> post("country/region/city", badCityName)).getStatus();
        assertEquals(BAD_REQUEST, status);
        List<String> cityNames = getCityNames();
        assertEquals(4, cityNames.size());
        assertFalse(cityNames.contains("co"));
    }

    //CREATE COUNTRY REGION CITY FAILS , NAME TOO SHORT on CITY
    @Test
    public void should_POST_city_add_new_country_new_region_new_city_too_short_validation_fails() {
        JsonObject badCountryRegionCityName = Json.createObjectBuilder()
            .add("countryName", "United Kingdom")
            .add("countryLanguage", "English")
            .add("regionName", "Midwest")
            .add("cityName", "Lo") // Test on CityName
            .build();
        HttpStatus status = assertThrows(ResponseStatusException.class, () -> post("country/region/createCountryRegionCity", badCountryRegionCityName)).getStatus();

        assertEquals(BAD_REQUEST, status);
        List<String> countryNames = getCountryNames();
        assertEquals(3, countryNames.size());
        assertFalse(countryNames.contains("United Kingdom"));

        List<String> regionNames = getRegionNames();
        assertEquals(2, regionNames.size());
        assertFalse(regionNames.contains("Midwest"));

        List<String> cityNames = getCityNames();
        assertEquals(4, cityNames.size());
        assertFalse(cityNames.contains("London"));
    }

    //ALEX PART 6 TEST
    @Test
    public void should_POST_root_region_name_too_short_validation_fails() {
        JsonObject badRegionName = Json.createObjectBuilder()
            .add("country", Json.createObjectBuilder()
                .add("name", "United Kingdom")
                .add("language", "English")
                .build())
            .add("region", Json.createObjectBuilder()
                .add("name", "ff")
                .build())
            .build();
        HttpStatus status = assertThrows(ResponseStatusException.class, () -> post("country/region", badRegionName))
            .getStatus();
        assertEquals(BAD_REQUEST, status);

        List<String> countryNames = getCountryNames();
        assertEquals(3, countryNames.size());

        List<String> regionNames = getRegionNames();
        assertEquals(2, regionNames.size());
    }
}