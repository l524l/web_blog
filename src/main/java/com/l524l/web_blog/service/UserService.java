package com.l524l.web_blog.service;

import com.l524l.web_blog.exception.EmailExistError;
import com.l524l.web_blog.exception.PasswordConfirmError;
import com.l524l.web_blog.exception.UserExistError;
import com.l524l.web_blog.models.User;

import java.util.List;

public interface UserService {
    void deleteUser(long ID);
    List<User> getByName(String name);
    User getById(long ID);
    User getByEmail(String email);
    User saveUser(User user) throws PasswordConfirmError, UserExistError, EmailExistError;
    User updateUser(User user);
    List<User> getAll();

    boolean activateUser(String code);
}
