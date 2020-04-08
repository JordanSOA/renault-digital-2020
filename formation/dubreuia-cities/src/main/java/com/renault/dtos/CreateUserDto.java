package com.renault.dtos;

import java.util.List;

public class CreateUserDto {
    private int id;
    private String name;
    private List<String> citiesNames;


    public CreateUserDto() {
    }

    public CreateUserDto(String name, List<String> citiesNames) {
        this.name = name;
        this.citiesNames = citiesNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCitiesNames() {
        return citiesNames;
    }

    public void setCitiesNames(List<String> citiesNames) {
        this.citiesNames = citiesNames;
    }
}
