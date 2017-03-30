package org.apples.mvpviews;

import javax.annotation.PostConstruct;

import org.apples.mvpviews.HouseEvents.HouseCountClicked;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@ViewScope
@SpringComponent
public class HouseView extends VerticalLayout {

	private static final long serialVersionUID = -5454028030887974247L;

	private final ViewEventBus eventBus;

	Label street = new Label();
	Label house = new Label();
	Label counter = new Label();
	Button doCount = new Button("Do some counting");

	public HouseView(ViewEventBus eventBus) {
		this.eventBus = eventBus;
	}

	@PostConstruct
	public void init() {
		street.setCaption("Street:");
		house.setCaption("House:");
		counter.setCaption("Counter:");
		doCount.addClickListener(this::doCountClicked);
		this.addComponent(street);
		this.addComponent(house);
		this.addComponent(counter);
		this.addComponent(doCount);
	}

	public void doCountClicked(ClickEvent event) {
		eventBus.publish(this, new HouseCountClicked());
	}
	
	public void updateStreet(String streetId) {
		street.setValue(streetId);
	}

	public void updateHouse(String houseId) {
		house.setValue(houseId);
	}

	public void updateCounter(String count) {
		counter.setValue(count);
	}
}
