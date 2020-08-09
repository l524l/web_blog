package com.l524l.web_blog.service;

import com.l524l.web_blog.models.User;

import java.util.List;

public interface UserService {
    void deleteUser(long ID);
    List<User> getByName(String name);
    User getById(long ID);
    User saveUser(User user);
    User updateUser(User user);
    List<User> getAll();

    boolean activateUser(String code);
}
