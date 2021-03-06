package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Categories;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.service.impl.PostServiceImpl;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class BlogController {
    private static final Logger log = Logger.getLogger(BlogController.class);

    final private PostServiceImpl postService;
    final private UserServiceImpl userService;

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
    public String addPost(@AuthenticationPrincipal User user,
                          @Valid @ModelAttribute Post post,
                          BindingResult bindingResult,
                          Model model) {
        String title = post.getTitle();
        String anons = post.getAnons();
        String full_text = post.getFull_text();
        if (bindingResult.hasErrors()) {
            FieldError titleError = bindingResult.getFieldError("title");
            FieldError anonsError = bindingResult.getFieldError("anons");
            FieldError textError = bindingResult.getFieldError("full_text");
            model.addAttribute("titleError", titleError);
            model.addAttribute("anonsError", anonsError);
            model.addAttribute("textError", textError);
            return "add_page";
        } else {
            post.setTitle(title);
            post.setAnons(anons);
            post.setFull_text(full_text);
            post.setAuthor(user);
            post.setDate(LocalDateTime.now());
            postService.savePost(post);
            return "redirect:/blog";
        }
    }

    @GetMapping("/blog/post{ID}")
    public String blogMoreInfo(@AuthenticationPrincipal User user,
                               @PathVariable(value = "ID") long ID,
                               Model model) {
        Post post = postService.getById(ID);
        post.setViews(post.getViews() + 1);
        postService.savePost(post);
        model.addAttribute("authUser", user.getName());
        model.addAttribute("post", post);
        return "post_page";
    }

    @GetMapping("/blog/post{ID}/edit")
    public String blogEdit(@PathVariable(value = "ID") long ID,
                           @AuthenticationPrincipal User user,
                           Model model) {
        Post post = postService.getById(ID);
        if (user.getAuthorities().contains(Role.GOD) || ((post.getAuthor().getId() == user.getId()) && user.getAuthorities().contains(Role.ADMIN))) {
            model.addAttribute("post", post);
            return "post_edit";
        } else return "error";
    }

    @GetMapping("/blog/category")
    public String sortByCategory(@RequestParam(name = "category") Categories category, Model model) {
        model.addAttribute("posts", postService.getByCategories(category));

        return "blog_page";
    }

    @PostMapping("/find")
    public String findPost(@RequestParam(name = "title") String title, Model model) {
        model.addAttribute("posts", postService.getByTitle(title));

        return "blog_page";
    }

    @PostMapping("/blog/post{ID}/edit")
    public String editPost(@PathVariable(value = "ID") long ID,
                           @AuthenticationPrincipal User user,
                           @Valid @ModelAttribute Post post,
                           BindingResult bindingResult,
                           Model model) {
        Post post2 = postService.getById(ID);
        if (user.getAuthorities().contains(Role.GOD) || ((post2.getAuthor().getId() == user.getId()) && user.getAuthorities().contains(Role.ADMIN))) {
            if (bindingResult.hasErrors()) {
                FieldError titleError = bindingResult.getFieldError("title");
                FieldError anonsError = bindingResult.getFieldError("anons");
                FieldError textError = bindingResult.getFieldError("full_text");
                model.addAttribute("titleError", titleError);
                model.addAttribute("anonsError", anonsError);
                model.addAttribute("textError", textError);

                model.addAttribute("post", post);
                return "post_edit";
            } else {
                post2.setTitle(post.getTitle());
                post2.setAnons(post.getAnons());
                post2.setFull_text(post.getFull_text());
                postService.savePost(post2);
                return "redirect:/blog";
            }
        } else {
            return "error";
        }
    }

    @PostMapping("/blog/post{ID}/delete")
    public String editPost(@PathVariable(value = "ID") long ID,
                           @AuthenticationPrincipal User user,
                           Model model) {
        Post post = postService.getById(ID);
        if (user.getAuthorities().contains(Role.GOD) || ((post.getAuthor().getId() == user.getId()) && user.getAuthorities().contains(Role.ADMIN))) {
            postService.deletePost(ID);
            return "redirect:/blog";
        } else {
            return "error";
        }
    }
}