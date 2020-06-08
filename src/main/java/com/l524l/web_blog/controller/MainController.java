package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/")
    public String greeting(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        List<Post> posts1 = new LinkedList<Post>((Collection<? extends Post>) posts);
        Collections.reverse(posts1);
        LinkedList<Post> postLinkedList = new LinkedList<Post>();
        Iterator<Post> iter = posts1.iterator();
        for (int i = 0; i < 6 && posts.iterator().hasNext() ;i++){
            postLinkedList.add(iter.next());
        }


        model.addAttribute("posts", postLinkedList);
        return "main_page";
    }

}