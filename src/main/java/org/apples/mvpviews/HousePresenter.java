package org.apples.mvpviews;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apples.mvpviews.HouseEvents.HouseCountClicked;
import org.apples.mvpviews.HouseEvents.HouseEntered;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus.ViewEventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.mvp.Presenter;

import com.vaadin.spring.annotation.ViewScope;

@ViewScope
@Component
public class HousePresenter extends Presenter<HouseView> implements Serializable {

	private static final long serialVersionUID = -3920064481914323105L;

	private final HouseModel model;

	public HousePresenter(HouseView view, HouseModel model, ViewEventBus eventBus) {
		super(view, eventBus);
		this.model = model;
	}

	@PostConstruct()
	void init() {
		model.clear();
		getView().updateCounter(Integer.toString(model.getCounter()));
		getView().updateHouse("");
		getView().updateStreet("");
	}

	@EventBusListenerMethod
	public void enter(HouseEntered event) {
		getView().updateHouse(event.getHouseId());
		getView().updateStreet(event.getStreetId());
	}

	@EventBusListenerMethod
	public void enter(HouseCountClicked event) {
		model.increment();
		getView().updateCounter(Integer.toString(model.getCounter()));
	}

}
