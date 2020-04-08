package com.renault.services;

import com.renault.dtos.CityDto;
import com.renault.entities.City;
import com.renault.entities.Country;
import com.renault.entities.Region;
import com.renault.entities.User;
import com.renault.repositories.CityRepository;
import com.renault.repositories.CountryRepository;
import com.renault.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Optional<City> getCity(int id) {
        return cityRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(City city) {
        cityRepository.save(city);
    }

    @Override
    @Transactional
    public void save(Country country, Region region, City city) {
        countryRepository.save(country);
        regionRepository.save(region);
        cityRepository.save(city);
    }

    @Override
    public List<City> findByName(List<String> cities) {
        List<City> cityList = new ArrayList<>();
        for (String city : cities) {
            cityList.add(cityRepository.findByName(city));
        }
        return cityList;
    }

    @Override
    public List<CityDto> deleteCity(City city) {
        Region region = city.getRegion();
        city.getUsers().stream()
            .filter(user -> user.getFollowedCities().contains(city))
            .forEach(user -> user.getFollowedCities().remove(city));

        region.getCities().remove(city);

        cityRepository.delete(city);
        List<CityDto> cityDtos = region.getCities().stream().map(cityInRegion -> new CityDto(cityInRegion.getId(), cityInRegion.getName())).collect(Collectors.toList());
        return cityDtos;
    }
}
