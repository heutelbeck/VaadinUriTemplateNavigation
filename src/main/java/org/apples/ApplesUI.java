package org.apples;

import org.apples.mvpviews.House;
import org.apples.views.Apple;
import org.apples.views.Apples;
import org.apples.views.Core;
import org.apples.views.Cores;

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

@SpringUI
@Theme("apples")
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
		navigationBar.addComponent(createNavigationButton("List all apples", Apples.NAME));
		navigationBar.addComponent(createNavigationButton("Show one apple", Apple.getViewName("12345")));
		navigationBar.addComponent(createNavigationButton("List cores of apple", Cores.getViewName("9874")));
		navigationBar
				.addComponent(createNavigationButton("Show one core of apple", Core.getViewName("d321", "c5762")));
		navigationBar.addComponent(createNavigationButton("House With MVP", House.getViewName("098765", "asf31d")));

		root.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

	}

	private Button createNavigationButton(String caption, final String viewName) {
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
