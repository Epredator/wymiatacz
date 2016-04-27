package com.innovationpassport.util;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.innovationpassport.MainUI;
import com.innovationpassport.model.Shift;
import com.innovationpassport.widgetset.client.model.Location;
import com.innovationpassport.widgetset.client.model.Ticket;
import com.innovationpassport.widgetset.client.model.Violation;
import com.vaadin.ui.JavaScript;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class DataUtil {

    private static final int RANDOM_SHIFT_COUNT = 700;
    private static final int HOUR_IN_MILLIS = 1000 * 60 * 60;
    private static final List<String> NAMES = Arrays.asList("Michał Ciszewski",
            "Magda Ciszewska");

    /**
     * Generate a collection of random shifts.
     * 
     * @return
     */
    public static Collection<Shift> generateRandomShifts() {
        Random random = new Random();

        Collection<Shift> result = Lists.newArrayList();
        for (int i = 0; i < RANDOM_SHIFT_COUNT; i++) {
            Shift shift = new Shift();

            shift.setArea("Mazowieckie".charAt(random.nextInt(3))
                    + String.valueOf(random.nextInt(4) + 1));

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, random.nextInt(1000));
            cal.set(Calendar.MINUTE, 0);
            shift.setDate(cal.getTime());

            shift.setDurationMillis(HOUR_IN_MILLIS + random.nextInt(8)
                    * HOUR_IN_MILLIS);

            shift.setName(NAMES.get(random.nextInt(NAMES.size())));

            result.add(shift);
        }
        return result;
    }

    private static final int RANDOM_TICKETS_COUNT = 50;

    public static Collection<Ticket> generateDummyTickets() {

        final List<Location> locations = Lists.newArrayList();

        locations
                .add(createLocation("Ząbkowska 43", 52.2502163, 21.0356212));
        locations.add(createLocation("Kijowska 20", 52.2466430, 21.0335183));
        locations.add(createLocation("Saska 21", 52.2371302 , 21.0620248));
        locations.add(createLocation("Solec 15", 52.2325308,  21.0387433));
        locations.add(createLocation("Dobra 8", 52.2372222 , 21.0301870 ));
        locations.add(createLocation("Czerniakowska 7", 52.2253810, 21.0357875));
        locations.add(createLocation("Łazienkowska 1", 52.2208921, 21.0394996));
        locations.add(createLocation("Czeska 6", 52.2041614, 21.0424125));
        locations.add(createLocation("Rydgiera 23", 52.2583866, 20.9731472));
        locations.add(createLocation("Śmiała 51", 52.2661811, 20.9914988));
        locations.add(createLocation("Karolkowa 25", 52.2341603, 20.9760493));

        Collection<Ticket> result = Lists.newArrayList();
        for (Location location : locations) {
            result.add(createRandomTicket(location));
        }

        for (int i = 0; i < RANDOM_TICKETS_COUNT; i++) {
            result.add(createRandomTicket(null));
        }

        return result;
    }

    private static Ticket createRandomTicket(final Location location) {
        final Random random = new Random();
        final Ticket ticket = new Ticket();

        Calendar cal = Calendar.getInstance();

        if (location == null) {
            cal.add(Calendar.HOUR, -(30 + random.nextInt(70)));
            ticket.setNotes("Dummy notes");
            ticket.setLocation(createDummyLocation());
        } else {
            cal.add(Calendar.HOUR, -random.nextInt(24));
            ticket.setLocation(location);
            ticket.setNotes("Notes for " + location.getAddress());
        }
        cal.set(Calendar.MINUTE, 0);
        ticket.setTimeStamp(cal.getTime());

        ticket.setImageUrl("VAADIN/themes/parking/tickets/" + 1 + ".jpg");
        ticket.setThumbnailUrl("VAADIN/themes/parking/tickets/" + 1
                + "thumbnail.jpg");
        ticket.setImageIncluded(true);
        ticket.setRegisterPlateNumber("ABC-" + (random.nextInt(800) + 100));

        ticket.setViolation(Violation.values()[random.nextInt(Violation
                .values().length)]);

        ticket.setMyTicket(random.nextDouble() < 0.1);

        ticket.setArea("ABC".charAt(random.nextInt(3))
                + String.valueOf(random.nextInt(4) + 1));
        return ticket;
    }

    private static Location createDummyLocation() {
        final Random random = new Random();

        double lat = MainUI.getApp().getCurrentLatitude();
        double lon = MainUI.getApp().getCurrentLongitude();

        Location location = new Location();
        location.setAddress("Test");

        double latitude = lat + (random.nextDouble() - 0.5) * 0.1;
        double longitude = lon + (random.nextDouble() - 0.5) * 0.1;
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    private static Location createLocation(final String address,
            final double latitude, final double longitude) {
        Location location = new Location();
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static void persistTickets(final List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                DataUtil.persistTicket(ticket);
            }
        }
        StringBuilder sb = new StringBuilder(tickets.size() + " ");
        sb.append("ticket");
        if (tickets.size() > 1) {
            sb.append("s");
        }
        sb.append(" saved");

        JavaScript.eval("window.alert('" + sb + "')");
    }

    public static void persistTicket(final Ticket ticket) {
        ticket.setMyTicket(true);
        Location location = ticket.getLocation();
        if (location.getLatitude() == 0.0 || location.getLongitude() == 0.0) {
            determineTicketLocation(ticket);
        }
        MainUI.getTicketContainer().addItem(ticket);
    }

    private static void determineTicketLocation(final Ticket ticket) {
        double latitude = MainUI.getApp().getCurrentLatitude();
        double longitude = MainUI.getApp().getCurrentLongitude();

        try {
            // Try to determine the coordinates using google maps api
            String address = ticket.getLocation().getAddress();
            if (address != null) {
                StringBuilder str = new StringBuilder(
                        "http://maps.google.com/maps/api/geocode/json?address=");
                str.append(address.replaceAll(" ", "+"));
                str.append("&sensor=false");

                URL url = new URL(str.toString());
                URLConnection urlc = url.openConnection();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(
                        urlc.getInputStream()));

                String line;
                final StringBuilder builder = new StringBuilder(2048);
                builder.append("[");
                while ((line = bfr.readLine()) != null) {
                    builder.append(line);
                }
                builder.append("]");
                final JsonArray jsa = (JsonArray) Json
                        .parse(builder.toString());
                final JsonObject jo = (JsonObject) jsa.get(0);
                JsonArray results = jo.getArray("results");
                JsonObject geometry = results.getObject(0)
                        .getObject("geometry");
                JsonObject loc = geometry.getObject("location");
                latitude = loc.getNumber("lat");
                longitude = loc.getNumber("lng");
            }
        } catch (Exception e) {
            // Ignore
        }

        ticket.getLocation().setLatitude(latitude);
        ticket.getLocation().setLongitude(longitude);

    }

}
