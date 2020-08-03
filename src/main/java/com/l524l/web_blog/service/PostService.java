package com.l524l.web_blog.service;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.models.enumes.Categories;

import java.util.List;

public interface PostService {
    void deletePost(long ID);
    List<Post> getByTitle(String title);
    Post getById(long ID);
    Post savePost(Post post);
    List<Post> getAll();
    List<Post> getByCategories(Categories categories);
}
