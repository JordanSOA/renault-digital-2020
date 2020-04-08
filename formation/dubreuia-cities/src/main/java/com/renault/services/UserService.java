package com.renault.services;

import com.renault.entities.User;

public interface UserService {
    void makeUserFollowCity(int userId, int cityId);

    void makeUserUnFollowCity(int userId, int cityId);

    User getUserById(int userId);

    void create(User user);
}
