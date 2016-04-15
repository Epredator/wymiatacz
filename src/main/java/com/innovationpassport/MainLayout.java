package com.innovationpassport;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class MainLayout extends MainLayoutDesign {

    public MainLayout() {
        Navigator navigator = new Navigator(UI.getCurrent(), contentPanel);
        navigator.addView(StatsView.VIEW_NAME, StatsView.class);
        navigator.addView(PluginsView.VIEW_NAME, PluginsView.class);
        navigator.addView(PermissionsView.VIEW_NAME, PermissionsView.class);
        navigator.addView(OrderView.VIEW_NAME, OrderView.class);

        menuButton1.addClickListener(event -> doNavigate(StatsView.VIEW_NAME));
        menuButton2
                .addClickListener(event -> doNavigate(PluginsView.VIEW_NAME));
        menuButton3.addClickListener(
                event -> doNavigate(PermissionsView.VIEW_NAME));
        menuButton4.addClickListener(event -> doNavigate(OrderView.VIEW_NAME));
        menuButton5.addClickListener(event -> doNavigate(StatsView.VIEW_NAME));

        if (navigator.getState().isEmpty()) {
            navigator.navigateTo(StatsView.VIEW_NAME);
        } else {
            navigator.navigateTo(navigator.getState());
        }
    }

    private void doNavigate(String viewName) {
        getUI().getNavigator().navigateTo(viewName);
    }
}
