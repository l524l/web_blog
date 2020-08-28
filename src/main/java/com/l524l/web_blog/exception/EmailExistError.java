package com.l524l.web_blog.exception;

public class EmailExistError extends Exception{
    public EmailExistError(){
        super("Email is exist");
    }
}
