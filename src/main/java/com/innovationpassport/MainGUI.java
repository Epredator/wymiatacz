package com.innovationpassport;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

@SpringUI
@Title("PosprzatajTo")
@SuppressWarnings("serial")
@DesignRoot
/**
 *
 */
@Theme("mytheme")
public class MainGUI extends UI {

	private double currentLatitude = 52.2296756;
	private double currentLongitude = 21.012228700000037;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setContent(new MainLayout());

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainGUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	/**
	 * A typed version of {@link UI#getCurrent()}
	 *
	 * @return the currently active PosprzatajTo UI.
	 */
	public static MainGUI getApp() {
		return (MainGUI) UI.getCurrent();
	}

	public double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public double getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}
}



