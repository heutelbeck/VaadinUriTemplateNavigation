package org.apples.mvpviews;

import org.springframework.stereotype.Component;

import com.vaadin.spring.annotation.ViewScope;

@ViewScope
@Component
public class HouseModel {
	int counter;

	public void clear() {
		counter = 0;
	}

	public void increment() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}

}
