package com.renault.services;

import com.renault.entities.Country;
import com.renault.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityService cityService;

    @Override
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> getCountry(int id) {
        return countryRepository.findById(id);
    }

    public Country getCountryByName(String name){
        return countryRepository.findbyName(name);
    }

    @Override
    @Transactional
    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public void deleteCountry(int id) {
        Country country = countryRepository.findById(id).orElseThrow();
        country.getRegions().stream().forEach(region -> {
            region.getCities().stream().forEach(city -> {
                cityService.deleteCity(city);
            });
        });
        countryRepository.deleteById(id);
    }

}
