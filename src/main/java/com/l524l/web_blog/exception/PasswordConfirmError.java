package com.l524l.web_blog.exception;

public class PasswordConfirmError extends Exception {
    public PasswordConfirmError(){
        super("Password mismatch");
    }
}
