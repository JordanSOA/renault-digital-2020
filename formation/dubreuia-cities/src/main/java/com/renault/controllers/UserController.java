package com.renault.controllers;

import com.renault.dtos.CityDto;
import com.renault.dtos.CreateUserDto;
import com.renault.dtos.UserDto;
import com.renault.entities.City;
import com.renault.entities.User;
import com.renault.services.CityService;
import com.renault.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;

    @PostMapping("/{userId}/followCity/{cityId}")
    public void makeUserFollowCityById(@PathVariable("userId") int userId, @PathVariable("cityId") int cityId){
        userService.makeUserFollowCity(userId,cityId);
    }
    @DeleteMapping("/{userId}/followCity/{cityId}")
    public void makeUserUnFollowCityById(@PathVariable("userId") int userId, @PathVariable("cityId") int cityId){
        userService.makeUserUnFollowCity(userId,cityId);
    }

    @PostMapping("")
    public void createUser(@RequestBody  @Valid CreateUserDto createUserDto){
        List<City> cities = cityService.findByName(createUserDto.getCitiesNames());
        User user = new User(createUserDto.getName(), cities);
        userService.create(user);
    }
    @GetMapping("/{userId}/followCity")
    public List<CityDto> getCitiesFollowedByUserById(@PathVariable("userId") int userId){
        User user = userService.getUserById(userId);
        List<CityDto> cityDtos = new ArrayList<>();
        user.getFollowedCities().stream().forEach(city -> {
            cityDtos.add(new CityDto(city.getId(),city.getName()));
        });
        /*
        for (City city: user.getFollowedCities()) {
        }
         */
        return cityDtos;
    }
}
