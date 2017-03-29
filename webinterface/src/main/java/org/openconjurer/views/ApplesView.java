package org.openconjurer.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = ApplesView.NAME)
public class ApplesView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "apples";
	
    @PostConstruct
    void init() {
        addComponent(new Label("This view lists all the apples. Fragment: '"+UI.getCurrent().getPage().getUriFragment()+"'"));
    }

	@Override
	public void enter(ViewChangeEvent event) {
		log.info("event: '{}'", ReflectionToStringBuilder.toString(event));	
		log.info("this: {}", this);
	}
	
	@PreDestroy
	public void destory() {
		log.info("I die {}",this);
	}
}