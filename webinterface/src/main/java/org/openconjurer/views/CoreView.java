package org.openconjurer.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.web.util.UriTemplate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = CoreView.TEMPLATE)
public class CoreView extends VerticalLayout implements View {
	SpringNavigator nav;

	private static final long serialVersionUID = 1L;

	public static final String TEMPLATE = "apples/{appleId}/cores/{coreId}";
	public static final UriTemplate URI_TEMPLATE = new UriTemplate(TEMPLATE);

	Label appleId = new Label();
	Label coreId = new Label();
	
	public static String getViewName(String appleId, String coreId) {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("appleId", appleId);
		vars.put("coreId", coreId);
		return URI_TEMPLATE.expand(vars).toString();
	}

	@PostConstruct
	void init() {
		addComponent(new Label("This views shows an individual core in a specific apple  Fragment: '"
				+ UI.getCurrent().getPage().getUriFragment() + "'"));
		addComponent(appleId);
		appleId.setCaption("Apple ID:");
		addComponent(coreId);
		coreId.setCaption("Core ID:");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		log.info("event: '{}'", ReflectionToStringBuilder.toString(event));
		Map<String, String> map = URI_TEMPLATE.match(event.getViewName());
		for (Entry<String, String> entry : map.entrySet()) {
			log.info("key = '{}' value='{}'", entry.getKey(), entry.getValue());
		}
		appleId.setValue(map.get("appleId"));
		coreId.setValue(map.get("coreId"));
		log.info("this: {}", this);
	}
	
	@PreDestroy
	public void destory() {
		log.info("I die {}", this);
	}
}