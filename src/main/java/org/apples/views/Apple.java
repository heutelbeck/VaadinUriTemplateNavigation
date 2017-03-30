package org.apples.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriTemplate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = Apple.TEMPLATE)
public class Apple extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Apple.class);

	public static final String TEMPLATE = "apples/{appleId}";
	public static final UriTemplate URI_TEMPLATE = new UriTemplate(TEMPLATE);
	Label appleId = new Label();

	public static String getViewName(String appleId) {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("appleId", appleId);
		return URI_TEMPLATE.expand(vars).toString();
	}

	@PostConstruct
	void init() {
		addComponent(new Label("This views shows the apple with a specific identifier  Fragment: '"
				+ UI.getCurrent().getPage().getUriFragment() + "'"));
		addComponent(appleId);
		appleId.setCaption("Apple ID:");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Map<String, String> map = URI_TEMPLATE.match(event.getViewName());
		for (Entry<String, String> entry : map.entrySet()) {
			LOGGER.info("key = '{}' value='{}'", entry.getKey(), entry.getValue());
		}
		appleId.setValue(map.get("appleId"));

		LOGGER.info("this: {}", this);
	}

	@PreDestroy
	public void destory() {
		LOGGER.info("I die {}", this);
	}
}