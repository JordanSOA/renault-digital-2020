package com.renault.controllers;

import com.renault.dtos.CountryRegionDto;
import com.renault.dtos.RegionDto;
import com.renault.entities.Country;
import com.renault.entities.Language;
import com.renault.entities.Region;
import com.renault.services.CountryService;
import com.renault.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping
public class RegionController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @GetMapping("/country/{countryId}/region")
    public List<RegionDto> getRegionsForCountry(@PathVariable("countryId") int countryId) {
        Country country = countryService.getCountry(countryId).orElseThrow();
        return country.getRegions().stream()
            .map(region -> new RegionDto(region.getId(), region.getName()))
            .collect(Collectors.toList());
    }

    @GetMapping("/country/region/{id}")

    public RegionDto getRegion(@PathVariable("id") int id) {
        Region region = regionService.getRegion(id).orElseThrow();
        return new RegionDto(region.getId(), region.getName());
    }

    @PostMapping("/country/{countryId}")
    public List<RegionDto> createRegion(@PathVariable("countryId") int countryId, @RequestBody @Valid RegionDto regionDto){
        Country country = countryService.getCountry(countryId).orElseThrow();
        regionService.saveRegion(new Region(regionDto.getName(), country));
        return getRegionsForCountry(country.getId());
    }

    @PostMapping("/country/region")
    public void createCountryRegion(@RequestBody @Valid CountryRegionDto countryRegionDto) {
        String countryName = countryRegionDto.getCountry().getName();
        String countryLanguage = countryRegionDto.getCountry().getLanguage();
        String regionName = countryRegionDto.getRegion().getName();
        Country country = new Country(Language.fromName(countryLanguage).orElseThrow(), countryName);
        Region region = new Region(regionName, country);
        regionService.saveCountryRegion(country, region);
    }
}