package com.renault.dtos;

import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class CountryDto {

    private int id;

    @NotNull
    //@Enumerated(EnumType.STRING)
    @Length(min = 2, max = 16, message = "Not proper length")
    private String language;

    @NotNull
    @Length(min = 3, max = 512, message = "Not proper length")
    private String name;

    public CountryDto() {
    }

    public CountryDto(int id, String language, String name) {
        this.id = id;
        this.language = language;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CountryDto{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
