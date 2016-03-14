package com.innovatepassport;

import com.innovatepassport.gui.MainTabsheet;
import com.innovatepassport.model.Ticket;
import com.innovatepassport.model.User;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
@SpringUI
@Theme("valo")
@Title("PosprzatajTo")
@SuppressWarnings("serial")
public class MainGUI extends UI {

	private final UserRepository repo;

	private final UserEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	private double currentLatitude = 52.2296756;
	private double currentLongitude = 21.012228700000037;
	private User user;

	private BeanItemContainer<Ticket> ticketContainer;

	/**
	 * A typed version of {@link UI#getCurrent()}
	 *
	 * @return the currently active PosprzatajTo UI.
	 */
	public static MainGUI getApp() {
		return (MainGUI) UI.getCurrent();
	}

	@Autowired
	public MainGUI(UserRepository repo, UserEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New user", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
//		ticketContainer = new BeanItemContainer<Ticket>(Ticket.class, DataUtil.generateDummyTickets());
//		user.setFirstName("Jan");        // set static default user name
//		user.setLastName("Kowalski");    //TODO prepare user panel to log in app.


		// build layout
//		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
//		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
//		setContent(mainLayout);

		setContent(new MainTabsheet());

		// Configure layouts and components
//		actions.setSpacing(true);
//		mainLayout.setMargin(true);
//		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName");

		filter.setInputPrompt("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listCustomers(e.getText()));

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editCustomer((User) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new User("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	private void listCustomers(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(
					new BeanItemContainer(User.class, repo.findAll()));
		}
		else {
			grid.setContainerDataSource(new BeanItemContainer(User.class,
					repo.findByLastNameStartsWithIgnoreCase(text)));
		}
	}// end::listUsers[]


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
