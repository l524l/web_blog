package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.service.PostService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController {
    private static final Logger log = Logger.getLogger(MainController.class);
    final private PostService postService;

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
    @GetMapping("/error")
    public String error(Model model){
        return "error";
    }
    @GetMapping("/about")
    public String aboutPage(Model model){
        return "about";
    }

}