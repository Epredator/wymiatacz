package com.innovationpassport.widgetset.client.model;

/**
 * Created by Antilamer on 05.05.2016.
 */
public enum Regions {
    MAZ("Mazowieckie"), KRK("Małopolskie"), SWK("Świętokrzyskie");


    private final String caption;

    private Regions(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }
}
