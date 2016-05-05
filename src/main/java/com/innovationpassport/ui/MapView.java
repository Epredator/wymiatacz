package com.innovationpassport.ui;

import com.innovationpassport.MainUI;
import com.innovationpassport.widgetset.client.model.Location;
import com.innovationpassport.widgetset.client.model.Ticket;
import com.vaadin.addon.touchkit.extensions.Geolocator;
import com.vaadin.addon.touchkit.extensions.PositionCallback;
import com.vaadin.addon.touchkit.gwt.client.vcom.Position;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import org.vaadin.addon.leaflet.*;
import org.vaadin.addon.leaflet.shared.Point;

import java.util.*;
import java.util.Calendar;

public class MapView extends CssLayout implements PositionCallback,
        LeafletClickListener {

    private LMap map;
    private Button locatebutton;
    private final LMarker you = new LMarker();

    private final BeanItemContainer<Ticket> ticketContainer = MainUI
            .getTicketContainer();

    @Override
    public void attach() {
        super.attach();
        if (map == null) {
            buildView();
        }
        updateMarkers();
    };

    private void buildView() {
        setCaption("Map");
        addStyleName("mapview");
        setSizeFull();

        map = new LMap();
        LTileLayer mapBoxTiles = new LTileLayer(
                "https://api.mapbox.com/styles/v1/epredator/cinume4fm00lcc7m5xqeaqstx/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZXByZWRhdG9yIiwiYSI6ImNpbHBsZW05eTAwM3d1emx1anNnNGNmcmUifQ.ngFbn8yJ5aPs0CtLw-2SLw");
        mapBoxTiles.setDetectRetina(true);
        map.addLayer(mapBoxTiles);

        map.setAttributionPrefix("wymiatacz.com powered by <a href=\"leafletjs.com\">Leaflet</a> — &copy; <a href='http://osm.org/copyright'>OpenStreetMap</a> contributors");
        map.setImmediate(true);
        map.setSizeFull();
        map.setZoomLevel(15);

        addComponent(map);

        you.setPoint(new Point(52.2502163, 21.0356212));
        setCenter();

        locatebutton = new Button("", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                Geolocator.detect(MapView.this);
            }
        });
        locatebutton.addStyleName("locatebutton");
        locatebutton.setWidth(30, Unit.PIXELS);
        locatebutton.setHeight(30, Unit.PIXELS);
        locatebutton.setDisableOnClick(true);
        addComponent(locatebutton);
    }

    public final void updateMarkers() {
        List<Ticket> tickets = ticketContainer.getItemIds();

        Iterator<Component> iterator = map.iterator();
        Collection<Component> remove = new ArrayList<Component>();
        while (iterator.hasNext()) {
            Component next = iterator.next();
            if (next instanceof LMarker) {
                remove.add(next);
            }
        }
        for (Component component : remove) {
            map.removeComponent(component);
        }

        you.setPoint(new Point(MainUI.getApp().getCurrentLatitude(),
                MainUI.getApp().getCurrentLongitude()));
        if (you.getParent() == null) {
            map.addComponent(you);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        for (Ticket ticket : tickets) {
            if (ticket.getTimeStamp().after(cal.getTime())) {
                Location location = ticket.getLocation();

                LMarker leafletMarker = new LMarker(location.getLatitude(),
                        location.getLongitude());
                leafletMarker.setIcon(new ThemeResource("pin.png"));
                leafletMarker.setIconSize(new Point(24, 38));
                leafletMarker.setIconAnchor(new Point(11, 38));
                leafletMarker.setData(ticket);
                leafletMarker.addClickListener(this);

                map.addComponent(leafletMarker);
            }
        }
    }

    @Override
    public void onSuccess(final Position position) {
        MainUI app = MainUI.getApp();
        app.setCurrentLatitude(position.getLatitude());
        app.setCurrentLongitude(position.getLongitude());

        setCenter();

        locatebutton.setEnabled(true);
    }

    private void setCenter() {
        if (map != null) {
            map.setCenter(you.getPoint());
        }
    }

    @Override
    public void onFailure(final int errorCode) {
        Notification
                .show("Geolocation request failed. You must grant access for geolocation requests.",
                        Type.ERROR_MESSAGE);
    }

    private void showPopup(final Ticket ticket) {
        new TicketDetailPopover(ticket).showRelativeTo(this);
    }

    @Override
    public void onClick(final LeafletClickEvent event) {
        Object o = event.getSource();
        if (o instanceof AbstractComponent) {
            AbstractComponent component = (AbstractComponent) o;
            Ticket ticket = (Ticket) component.getData();
            showPopup(ticket);
        }
    }
}
