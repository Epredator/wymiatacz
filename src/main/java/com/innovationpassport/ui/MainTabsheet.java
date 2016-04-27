package com.innovationpassport.ui;

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet.Tab;

/**
 * This is the main view for wymiatacz.com application. It displays a tabbar via one
 * can choose one of the sub views.
 */
public class MainTabsheet extends TabBarView {

    public MainTabsheet() {
        /*
         * Populate main views
         */
        MapView mapView = new MapView();
        
        addTab(mapView, "maptab", "Mapa zgłoszeń");
        addTab(new TicketView(), "ticketstab", "Dodaj zgłoeszenie");
        addTab(new ShiftsView(), "shiftstab", "Zgłoszenia");
        addTab(new StatsView(), "statstab", "Raporty");
        setSelectedTab(mapView);
    }

    private void addTab(final Component component, final String styleName,
            final String caption) {
        Tab tab = addTab(component);
        tab.setStyleName(styleName);
        tab.setCaption(caption);
    }

}
