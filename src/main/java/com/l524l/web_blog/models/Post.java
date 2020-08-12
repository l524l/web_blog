package com.l524l.web_blog.models;

import com.l524l.web_blog.models.enumes.Categories;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;

    @Size(max = 50,message = "Максимальная длинна 50 символов")
    @NotBlank(message = "Поле должно быть заполнено")
    private String title;
    @Size(max = 100,message = "Максимальная длинна 100 символов")
    @NotBlank(message = "Поле должно быть заполнено")
    private String anons;
    @Size(max = 100000,message = "Слишком длинный пост! Максимальная длинна 100000 символов")
    @NotBlank(message = "Поле должно быть заполнено")
    private String full_text;
    private int views;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @JoinColumn(name = "categories")
    @Enumerated(EnumType.STRING)
    private Categories Categories;

    public Post() {
    }

    public Categories getCategories() {
        return Categories;
    }

    public void setCategories(Categories categories) {
        Categories = categories;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "NONE";
    }

    public Post(String title, String anons, String full_text, User author) {
        this.author = author;
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getID() {

        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
