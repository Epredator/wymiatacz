package com.innovationpassport.gui;

//import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.ui.*;

/**
 * User: trojnaradam@gmail.com
 * Date: 12.03.16
 * Time: 19:15
 */
public class MainTabsheet extends VerticalLayout {
  protected CssLayout header_bar;
  protected NativeButton user_menu;
  protected Label user_name_label;
  protected TextField search_field;
  protected HorizontalLayout main_area;
  protected CssLayout side_bar;
  protected NativeButton menuButton1;
  protected NativeButton menuButton2;
  protected NativeButton menuButton3;
  protected Panel scroll_panel;

//  private Tool toolbar = new Toolbar();
public MainTabsheet() {
        /*
         * Populate main views
         */
  TicketView ticketView = new TicketView();

  ticketView.addComponent(new TicketView());
  ticketView.addComponent(new MapView());
  ticketView.addComponent(new StatsView());
}



}
