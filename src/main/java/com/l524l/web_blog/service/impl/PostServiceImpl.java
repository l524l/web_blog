package com.l524l.web_blog.service.impl;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.repo.PostRepository;
import com.l524l.web_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void deletePost(long ID) {
        postRepository.deleteById(ID);
    }

    @Override
    public List<Post> getByTitle(String title) {
        List<Post> post = postRepository.findByTitle(title);
        return post;
    }

    @Override
    public Post getById(long ID) {
        Optional<Post> post = postRepository.findById(ID);
        return post.get();
    }

    @Override
    public Post savePost(Post post) {
        Post editPost = postRepository.save(post);
        return editPost;
    }

    @Override
    public List<Post> getAll() {
        postRepository.findAll();
        return postRepository.findAll();
    }
}
