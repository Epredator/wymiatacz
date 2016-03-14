package com.innovatepassport.util;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.innovatepassport.MainGUI;
import com.innovatepassport.model.Location;
import com.innovatepassport.model.Ticket;
import com.innovatepassport.model.Violation;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * User: trojnaradam@gmail.com
 * Date: 12.03.16
 * Time: 18:24
 */
public class DataUtil  {
  private static final int RANDOM_TICKETS_COUNT = 45;

  public static Collection<Ticket> generateDummyTickets() {

    final List<Location> locations = Lists.newArrayList();

    locations
        .add(createLocation("Warszawska 51", 52.2296756, 21.012228700000037));
    locations.add(createLocation("Czeska 20", 51.11026223, 19.99589876));
    locations.add(createLocation("Racaławicka 5-7", 51.50247491, 22.285459));
    locations.add(createLocation("Bananowa 15", 52.48793694, 23.32257199));
    locations.add(createLocation("Afrykańska 8", 51.48338754, 22.294472));
    locations.add(createLocation("Madrycka 7", 52.00467778, 22.273057));
    locations.add(createLocation("Tunezyjska 6", 52.73170137, 22.263015));
    locations.add(createLocation("Kownacka 6", 53.19352323, 22.30829));
    locations.add(createLocation("Stalowa 23", 52.87490913, 22.32065));
    locations.add(createLocation("Wileńska 51", 52.25194354, 22.274259));
    locations.add(createLocation("Szwedzka 5", 52.18462351, 22.255848));
    locations.add(createLocation("Środkowa 39", 52.94097431, 22.275761));
    locations.add(createLocation("Radzymińska 3", 51.75048196, 22.284945));
    locations.add(createLocation("Wołomińska 3-5", 53.19352333, 22.305329));

    Collection<Ticket> result = Lists.newArrayList();
    for (Location location : locations) {
      result.add(createRandomTicket(location));
    }

    for (int i = 0; i < RANDOM_TICKETS_COUNT; i++) {
      result.add(createRandomTicket(null));
    }

    return result;
  }

  private static Location createLocation(final String address,
                                         final double latitude, final double longitude) {
    Location location = new Location();
    location.setAddress(address);
    location.setLatitude(latitude);
    location.setLongitude(longitude);
    return location;
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

    ticket.setImageUrl("ip/posprzatajto/tickets/" + 1 + ".jpg");
    ticket.setThumbnailUrl("ip/posprzatajto/tickets/" + 1
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

    double lat = MainGUI.getApp().getCurrentLatitude();
    double lon = MainGUI.getApp().getCurrentLongitude();

    Location location = new Location();
    location.setAddress("Test");

    double latitude = lat + (random.nextDouble() - 0.5) * 0.1;
    double longitude = lon + (random.nextDouble() - 0.5) * 0.1;
    location.setLatitude(latitude);
    location.setLongitude(longitude);

    return location;
  }
}
