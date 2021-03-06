package com.innovationpassport.widgetset.client.model;

public enum Violation {
    TECHNICAL_WASTE("Odpady techniczne"), FOOD_WASTE("Odpady żywności"), CHEMICAL_WASTE(
            "Odpady chemiczne");

    private final String caption;

    private Violation(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

}
