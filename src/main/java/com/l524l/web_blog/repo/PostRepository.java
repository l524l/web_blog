package com.l524l.web_blog.repo;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.models.enumes.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select post from Post post where post.title = :name")
    List<Post> findByTitle(@Param("name") String name);
    @Query("select post from Post post where post.Categories = :category")
    List<Post> findByCategories(@Param("category") Categories categories);
}
