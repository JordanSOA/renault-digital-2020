package com.renault.services;

import com.renault.entities.City;
import com.renault.entities.User;
import com.renault.repositories.CityRepository;
import com.renault.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public void makeUserFollowCity(int userId, int cityId) {
        User user = userRepository.findById(userId).orElseThrow();
        City city = cityRepository.findById(cityId).orElseThrow();
        user.getFollowedCities().add(city);
        userRepository.save(user);
    }

    @Override
    public void makeUserUnFollowCity(int userId, int cityId) {
        User user = userRepository.findById(userId).orElseThrow();
        City city = cityRepository.findById(cityId).orElseThrow();
        user.getFollowedCities().stream()
            .filter(followedCity -> city.getId() == followedCity.getId())
            .forEach(followedCity -> user.getFollowedCities().remove(followedCity));

        userRepository.save(user);
    }
}
