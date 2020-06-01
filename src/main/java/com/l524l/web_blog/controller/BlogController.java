package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
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
            postRepository.save(post);
            return "redirect:/blog";
        }
    }

    @GetMapping("/blog/post{ID}")
    public String blogMoreInfo(@PathVariable(value = "ID") long ID, Model model) {
        Optional<Post> post = postRepository.findById(ID);
        Post post2 = post.get();
        post2.setViews(post2.getViews() + 1);
        postRepository.save(post2);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "post_page";
    }

    @GetMapping("/blog/post{ID}/edit")
    public String blogEdit(@PathVariable(value = "ID") long ID, Model model) {
        Optional<Post> post = postRepository.findById(ID);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "post_edit";
    }

    @PostMapping("/blog/post{ID}/edit")
    public String editPost(@PathVariable(value = "ID") long ID,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "anons") String anons,
                           @RequestParam(name = "full_text") String full_text,
                           Model model) {

        Post post = postRepository.findById(ID).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/post{ID}/delete")
    public String editPost(@PathVariable(value = "ID") long ID, Model model) {
        Post post = postRepository.findById(ID).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }
}
