package com.l524l.web_blog.exception;

public class UserExistError extends Exception{
    public UserExistError(){
        super("User Exist");
    }
}
