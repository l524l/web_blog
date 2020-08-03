package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Categories;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.service.impl.PostServiceImpl;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class BlogController {

    final private PostServiceImpl postService;
    final private UserServiceImpl userService;

    @Autowired
    public BlogController(PostServiceImpl postService, UserServiceImpl userService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        List<Post> posts = postService.getAll();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        return "blog_page";
    }

    @PreAuthorize("hasAuthority('GOD') || hasAuthority('ADMIN')")
    @GetMapping("/blog/add")
    public String addPostPage(Model model) {
        model.addAttribute("post", new Post());
        return "add_page";
    }

    @PreAuthorize("hasAuthority('GOD') || hasAuthority('ADMIN')")
    @PostMapping("/blog/add")
    public String addPost(@AuthenticationPrincipal User user, @ModelAttribute Post post,
                          Model model) {
        String title = post.getTitle();
        String anons = post.getAnons();
        String full_text = post.getFull_text();

        if (title.trim().isEmpty() || anons.trim().isEmpty() || full_text.trim().isEmpty()){
            model.addAttribute("error","Все поля должны быть заполнены!");
            return "add_page";
        }else {
            post.setTitle(title);
            post.setAnons(anons);
            post.setFull_text(full_text);
            post.setAuthor(user);
            //post.setCategories(Collections.singleton(Categories.SPORT));
            post.setDate(LocalDateTime.now());
            postService.savePost(post);
            return "redirect:/blog";
        }
    }

    @GetMapping("/blog/post{ID}")
    public String blogMoreInfo(@AuthenticationPrincipal User user, @PathVariable(value = "ID") long ID, Model model) {
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

    @GetMapping("/blog/category")
    public String sortByCategory(@RequestParam(name = "category") Categories category, Model model){
        model.addAttribute("posts", postService.getByCategories(category));

        return "blog_page";
    }

    @PostMapping("/find")
    public String findPost(@RequestParam(name = "title") String title, Model model){
        model.addAttribute("posts", postService.getByTitle(title));

        return "blog_page";
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