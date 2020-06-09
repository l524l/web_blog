package com.l524l.web_blog.service;

import com.l524l.web_blog.models.Post;

import java.util.List;

public interface PostService {
    void deletePost(long ID);
    List<Post> getByTitle(String title);
    Post getById(long ID);
    Post savePost(Post post);
    List<Post> getAll();
}
