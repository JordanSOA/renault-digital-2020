package com.renault.controllers;

import com.renault.dtos.CityDto;
import com.renault.dtos.CountryRegionCityDto;
import com.renault.entities.*;
import com.renault.services.CityService;
import com.renault.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class CityController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    /*
    public List<CountryDto> getCountries() {
        List<CountryDto> countries = new ArrayList<>();
        countryService.getCountries().stream().forEach( country -> {
            countries.add(new CountryDto(country.getId(), country.getLanguage().getName(), country.getName()));
        });
        return countries;
    }
     */
    @GetMapping("/country/region/{regionId}/city")
    public List<CityDto> getCitiesForRegion(@PathVariable("regionId") int regionId) {
        Region region = regionService.getRegion(regionId).orElseThrow();
        List<CityDto> cityDtos = new ArrayList<>();
        region.getCities().stream().forEach(city -> {
            cityDtos.add(new CityDto(city.getId(), city.getName()));
        });
        /*
        for (City city : region.getCities()) {
        }
        */
        return cityDtos;
    }

    @PutMapping("/country/region/city/{id}")
    public void updateCity(@PathVariable("id") int id, @RequestBody @Valid CityDto cityDto) {
        City city = cityService.getCity(id).orElseThrow();
        city.setName(cityDto.getName());
        cityService.save(city);
        getCitiesForRegion(city.getRegion().getId());
    }

    @DeleteMapping(value = "/country/region/city/{id}", produces = "application/json")
    @ResponseBody
    public List<CityDto> deleteCity(@PathVariable("id") int id) {
        City city = cityService.getCity(id).orElseThrow();
        return cityService.deleteCity(city);
    }

    @PostMapping("/country/region/city")
    @ResponseBody
    public List<CityDto> createCity(@RequestBody @Valid CityDto cityDto) {
        Region region = regionService.getRegion(cityDto.getId()).orElseThrow();
        cityService.save(new City(cityDto.getName(), region));
        return getCitiesForRegion(region.getId());
    }

    @PostMapping("/country/region/createCountryRegionCity")
    public void insertCountryRegionCity(@RequestBody @Valid CountryRegionCityDto dto) {
        Language countryLanguage = Language.fromName(dto.getCountryLanguage()).orElseThrow();
        Country country = new Country(countryLanguage, dto.getCountryName());
        Region region = new Region(dto.getRegionName(), country);
        City city = new City(dto.getCityName(), region);
        cityService.save(country, region, city);
    }

}
