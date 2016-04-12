package com.ip.wymiatacz.model;

/**
 * User: trojnaradam@gmail.com
 * Date: 12.03.16
 * Time: 18:14
 */
public enum Violation {
  PROHIBITED_LANDFILL("Wysypisko śmieci"),
  DESTROYED_TRASH("Zniszczony kosz na śmieci"),
  LARGE_GARBAGE("Śmieć o dużych gabarytach (elektronika, opony");

  private final String caption;

  private Violation(String caption) {
    this.caption = caption;
  }

  public String getCaption(){
    return caption;
  }
}
