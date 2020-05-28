package com.l524l.web_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class WebBlogApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = new
				ClassPathXmlApplicationContext(WebBlogApplication.class.getClassLoader().getResource("beans.xml").toExternalForm());
		System.out.println(ctx.getBean("test",Test.class).helloWorl());
		((ClassPathXmlApplicationContext) ctx).close();

		SpringApplication.run(WebBlogApplication.class, args);
	}

}
