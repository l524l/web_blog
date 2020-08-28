package com.l524l.web_blog.repo;

import com.l524l.web_blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select user from User user where user.name = :name")
    List<User> findByName(@Param("name") String name);
    @Query("select user from User user where user.email = :email")
    User findByEmail(@Param("email") String email);
    User findByActivationCode(String code);
}
