package org.openconjurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;

@SpringBootApplication
public class WebinterfaceApplication {

	@Autowired
	private ApplicationContext applicationContext;
//	@Autowired
//	private BeanDefinitionRegistry beanDefinitionRegistry;

	public static void main(String[] args) {
		SpringApplication.run(WebinterfaceApplication.class, args);
	}

	@Bean
	@Primary
	@UIScope
	SpringViewProvider templateViewProvider() {
		return new UriTemplateViewProvider(applicationContext, null);
	}
}
