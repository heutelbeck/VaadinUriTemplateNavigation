package org.apples;

import org.apples.views.AppleView;
import org.apples.views.ApplesView;
import org.apples.views.CoreView;
import org.apples.views.CoresView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringUI
@Theme("openconjurer")
@SpringViewDisplay
public class ApplesUI extends UI implements ViewDisplay {

	private static final long serialVersionUID = 1L;

	private Panel springViewDisplay;

	@Override
	protected void init(VaadinRequest request) {

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("List all apples", ApplesView.NAME));
		navigationBar.addComponent(createNavigationButton("Show one apple", AppleView.getViewName("12345")));
		navigationBar.addComponent(createNavigationButton("List cores of apple", CoresView.getViewName("9874")));
		navigationBar.addComponent(createNavigationButton("Show one core of apple", CoreView.getViewName("d321", "c5762")));

		root.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

	}

	private Button createNavigationButton(String caption, final String viewName) {
		log.info("{} -> {}", caption,viewName);
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}
}
