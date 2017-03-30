package org.apples;

import org.atmosphere.cpr.SessionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.support.ApplicationContextEventBroker;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class ApplesApplication {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	EventBus.ApplicationEventBus applicationEventBus;

	@Bean
	@Primary
	@UIScope
	SpringViewProvider templateViewProvider() {
		return new UriTemplateViewProvider(applicationContext, null);
	}
	
	/**
	 * Forward {@link org.springframework.context.ApplicationEvent}s to the
	 * {@link org.vaadin.spring.events.EventBus.ApplicationEventBus}.
	 */
	@Bean
	ApplicationContextEventBroker applicationContextEventBroker() {
		return new ApplicationContextEventBroker(applicationEventBus);
	}

	@Bean
	SessionSupport atmosphereSessionSupport() {
		return new SessionSupport();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApplesApplication.class, args);
	}
}
