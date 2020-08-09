package com.l524l.web_blog.service.impl;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.models.enumes.Categories;
import com.l524l.web_blog.repo.PostRepository;
import com.l524l.web_blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    final private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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

    @Override
    public List<Post> getByCategories(Categories categories) {
        List<Post> posts = postRepository.findByCategories(categories);
        return posts;
    }
}
