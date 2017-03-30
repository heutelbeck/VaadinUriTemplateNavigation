package org.apples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;

@SpringBootApplication
public class ApplesApplication {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(ApplesApplication.class, args);
	}

	@Bean
	@Primary
	@UIScope
	SpringViewProvider templateViewProvider() {
		return new UriTemplateViewProvider(applicationContext, null);
	}
}
