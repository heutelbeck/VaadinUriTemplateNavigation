package org.apples.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = Apples.NAME)
public class Apples extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Apples.class);

	public static final String NAME = "apples";
	
    @PostConstruct
    void init() {
        addComponent(new Label("This view lists all the apples. Fragment: '"+UI.getCurrent().getPage().getUriFragment()+"'"));
    }

	@Override
	public void enter(ViewChangeEvent event) {
		// NOP
	}
	
	@PreDestroy
	public void destory() {
		LOGGER.info("I die {}",this);
	}
}