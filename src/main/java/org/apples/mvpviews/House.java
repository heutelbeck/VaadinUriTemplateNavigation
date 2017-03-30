package org.apples.mvpviews;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apples.mvpviews.HouseEvents.HouseEntered;
import org.springframework.web.util.UriTemplate;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@SpringView(name = House.TEMPLATE)
public class House extends Panel implements View {
	private static final long serialVersionUID = 1L;

	public static final String STREET_ID = "streetId";
	public static final String HOUSE_ID = "houseId";
	public static final String TEMPLATE = "streets/{" + STREET_ID + "}/houses/{" + HOUSE_ID + "}";
	public static final UriTemplate URI_TEMPLATE = new UriTemplate(TEMPLATE);

	private final transient ViewEventBus eventBus;
	private final transient HousePresenter presenter;

	Label appleId = new Label();

	public static String getViewName(String streetId, String houseId) {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put(STREET_ID, streetId);
		vars.put(HOUSE_ID, houseId);
		return URI_TEMPLATE.expand(vars).toString();
	}

	public House(ViewEventBus eventBus, HousePresenter presenter) {
		this.eventBus = eventBus;
		this.presenter = presenter;
	}

	@PostConstruct
	void init() {
		setContent(presenter.getView());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Map<String, String> map = URI_TEMPLATE.match(event.getViewName());
		eventBus.publish(this, new HouseEntered(map.get(STREET_ID),map.get(HOUSE_ID)));
	}
	

}
