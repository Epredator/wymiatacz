package com.ip.wymiatacz.model;

/**
 * User: trojnaradam@gmail.com
 * Date: 12.03.16
 * Time: 18:21
 */
public class Location {
  private double longitude;
  private double latitude;
  private String address;

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
