package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/blog")
    public String blog(Model model) {
        List<Post> posts = postService.getAll();
        Collections.reverse(posts);

        model.addAttribute("posts", posts);
        return "blog_page";
    }

    @GetMapping("/blog/add")
    public String addPostPage(Model model) {
        return "add_page";
    }

    @PostMapping("/blog/add")
    public String addPost(@RequestParam(name = "title", defaultValue = "") String title,
                          @RequestParam(name = "anons", defaultValue = "") String anons,
                          @RequestParam(name = "full_text", defaultValue = "") String full_text,
                          Model model) {

        if (title.trim().isEmpty() || anons.trim().isEmpty() || full_text.trim().isEmpty()){
            model.addAttribute("error","Все поля должны быть заполнены!");
            return "add_page";
        }else {
            Post post = new Post(title.trim(), anons.trim(), full_text.trim());
            postService.savePost(post);
            return "redirect:/blog";
        }
    }

    @GetMapping("/blog/post{ID}")
    public String blogMoreInfo(@PathVariable(value = "ID") long ID, Model model) {
        Post post = postService.getById(ID);
        post.setViews(post.getViews() + 1);
        postService.savePost(post);
        model.addAttribute("post", post);

        return "post_page";
    }

    @GetMapping("/blog/post{ID}/edit")
    public String blogEdit(@PathVariable(value = "ID") long ID, Model model) {
        Post post = postService.getById(ID);
        model.addAttribute("post", post);
        return "post_edit";
    }

    @PostMapping("/find")
    public String findPost(@RequestParam(name = "title") String title, Model model){
        model.addAttribute("post", postService.getByTitle(title));

        return "post_page";
    }

    @PostMapping("/blog/post{ID}/edit")
    public String editPost(@PathVariable(value = "ID") long ID,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "anons") String anons,
                           @RequestParam(name = "full_text") String full_text,
                           Model model) {
        Post post = postService.getById(ID);
        if (title.trim().isEmpty() || anons.trim().isEmpty() || full_text.trim().isEmpty()){
            model.addAttribute("error","Все поля должны быть заполнены!");
            model.addAttribute("post", post);
            return "post_edit";
        }else {
            post.setTitle(title);
            post.setAnons(anons);
            post.setFull_text(full_text);
            postService.savePost(post);
            return "redirect:/blog";
        }
    }

    @PostMapping("/blog/post{ID}/delete")
    public String editPost(@PathVariable(value = "ID") long ID, Model model) {
        postService.deletePost(ID);
        return "redirect:/blog";
    }
}