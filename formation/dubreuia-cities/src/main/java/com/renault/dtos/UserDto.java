package com.renault.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserDto {

    private int id;

    @NotNull
    @Length(min = 3, max = 512, message = "Not proper length")
    private int name;

    public UserDto() {
    }

    public UserDto(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

}
