package com.l524l.web_blog.repo;

import com.l524l.web_blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
