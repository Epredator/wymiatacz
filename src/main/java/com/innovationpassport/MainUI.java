package com.innovationpassport;

import com.innovationpassport.ui.MainTabsheet;
import com.innovationpassport.util.DataUtil;
import com.innovationpassport.widgetset.client.model.Ticket;
import com.vaadin.addon.responsive.Responsive;
import com.vaadin.addon.touchkit.annotations.CacheManifestEnabled;
import com.vaadin.addon.touchkit.annotations.OfflineModeEnabled;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
//import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import fi.jasoft.qrcode.QRCode;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * The UI class for wymiatacz.com mvp.
 */
@Theme("wymiatacz")
@Widgetset("com.innovationpassport.widgetset.WymiataczWidgetset")
@PreserveOnRefresh
@Title("Wymiatacz.com -wymieć śmieci z Twojej okolicy!")
@OfflineModeEnabled
@CacheManifestEnabled
public class MainUI extends UI {

    /*
     * Default the location of map
     */
    private double currentLatitude = 52.2502163;
    private double currentLongitude = 21.0356212;
    private String user;
    private ParkingOfflineModeExtension offlineModeSettings;
    private BeanItemContainer<Ticket> ticketContainer;

    @Override
    public void init(VaadinRequest request) {
        ticketContainer = new BeanItemContainer<Ticket>(Ticket.class,
                DataUtil.generateDummyTickets());
        // Set a nice default for user for demo purposes.
        setUser("Zenon Grabik");

        setContent(new MainTabsheet());

        // Use custom offline mode
        offlineModeSettings = new ParkingOfflineModeExtension();
        offlineModeSettings.extend(this);
        offlineModeSettings.setPersistentSessionCookie(true);
        // Default is 10 secs.
        offlineModeSettings.setOfflineModeTimeout(15);

        new Responsive(this);

        if (request.getParameter("mobile") == null
                && !getPage().getWebBrowser().isTouchDevice()) {
            showNonMobileNotification();
        }
    }

    public void goOffline() {
        offlineModeSettings.goOffline();
    }

    /**
     * The location information is stored in Application instance to be
     * available for all components. It is detected by the map view during
     * application init, but also used by other maps in the application.
     *
     * @return the current latitude as degrees
     */
    public double getCurrentLatitude() {
        return currentLatitude;
    }

    /**
     * @return the current longitude as degrees
     * @see #getCurrentLatitude()
     */
    public double getCurrentLongitude() {
        return currentLongitude;
    }

    /**
     * @see #getCurrentLatitude()
     */
    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     * @see #getCurrentLatitude()
     */
    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    /**
     * A typed version of {@link UI#getCurrent()}
     *
     * @return the currently active Parking UI.
     */
    public static MainUI getApp() {
        return (MainUI) UI.getCurrent();
    }

    public static BeanItemContainer<Ticket> getTicketContainer() {
        return getApp().ticketContainer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private void showNonMobileNotification() {
        try {
            URL appUrl = Page.getCurrent().getLocation().toURL();
            String myIp = Inet4Address.getLocalHost().getHostAddress();
            String qrCodeUrl = appUrl.toString().replaceAll("localhost", myIp);

            QRCode qrCode = new QRCode(
                    "You appear to be running this demo on a non-portable device. "
                            + "wymiatacz.com is intended for touch devices primarily. "
                            + "Please read the QR code on your touch device to access the wymiatacz.com.",
                    qrCodeUrl);
            qrCode.setWidth("150px");
            qrCode.setHeight("150px");

            CssLayout qrCodeLayout = new CssLayout(qrCode);
            qrCodeLayout.setSizeFull();

            Window window = new Window(null, qrCodeLayout);
            window.setWidth(500.0f, Unit.PIXELS);
            window.setHeight(200.0f, Unit.PIXELS);
            window.addStyleName("qr-code");
            window.setModal(true);
            window.setResizable(false);
            window.setDraggable(false);
            addWindow(window);
            window.center();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
