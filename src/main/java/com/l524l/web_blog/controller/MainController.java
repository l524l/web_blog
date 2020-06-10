package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController {

    final private PostService postService;
    @Autowired
    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        List<Post> posts = postService.getAll();
        Collections.reverse(posts);
        LinkedList<Post> postLinkedList = new LinkedList<>();
        Iterator<Post> iter = posts.iterator();
        for (int i = 0;i < 6 && iter.hasNext();i++){
            postLinkedList.add(iter.next());
        }
        model.addAttribute("posts", postLinkedList);
        return "main_page";
    }

}