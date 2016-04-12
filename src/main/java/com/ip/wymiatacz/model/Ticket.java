package com.ip.wymiatacz.model;

import java.util.Date;

/**
 * User: trojnaradam@gmail.com
 * Date: 12.03.16
 * Time: 18:12
 */
public class Ticket {
  private Location location = new Location();
  private Date timeStamp = new Date();
  private String registerPlateNumber;
  private Violation violation;
  private String area;

  private String imageUrl;
  private String thumbnailUrl;
  private String notes;
  private boolean imageIncluded;
  private boolean myTicket;

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getRegisterPlateNumber() {
    return registerPlateNumber;
  }

  public void setRegisterPlateNumber(String registerPlateNumber) {
    this.registerPlateNumber = registerPlateNumber;
  }

  public Violation getViolation() {
    return violation;
  }

  public void setViolation(Violation violation) {
    this.violation = violation;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public boolean isImageIncluded() {
    return imageIncluded;
  }

  public void setImageIncluded(boolean imageIncluded) {
    this.imageIncluded = imageIncluded;
  }

  public boolean isMyTicket() {
    return myTicket;
  }

  public void setMyTicket(boolean myTicket) {
    this.myTicket = myTicket;
  }
}
