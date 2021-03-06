package com.innovationpassport.widgetset.client.ticketview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.Callback;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.innovationpassport.widgetset.client.model.Regions;
import com.vaadin.addon.touchkit.gwt.client.ui.DatePicker;
import com.vaadin.addon.touchkit.gwt.client.ui.DatePicker.Resolution;
import com.vaadin.addon.touchkit.gwt.client.ui.VSwitch;
import com.vaadin.addon.touchkit.gwt.client.ui.VerticalComponentGroupWidget;
import com.vaadin.client.ui.VTextField;
import com.innovationpassport.widgetset.client.model.Location;
import com.innovationpassport.widgetset.client.model.Ticket;
import com.innovationpassport.widgetset.client.model.Violation;

public class InformationLayout extends VerticalComponentGroupWidget {
    private final VSwitch useCurrentLocationSwitch;
    private com.google.gwt.geolocation.client.Position currentPosition;
    private final VTextField addressField;
    private Date date = new Date();
    private final ListBox violationBox;
    private final ListBox areaBox;
    private final TicketViewModuleListener listener;

    private void requestUserPosition() {
        Geolocation geolocation = Geolocation.getIfSupported();
        if (geolocation == null) {
            useCurrentLocationSwitch.setValue(false);
        } else {
            geolocation
                    .getCurrentPosition(new Callback<com.google.gwt.geolocation.client.Position, PositionError>() {
                        @Override
                        public void onSuccess(
                                final com.google.gwt.geolocation.client.Position result) {
                            currentPosition = result;
                            if (listener != null) {
                                listener.positionReceived(result
                                        .getCoordinates().getLatitude(), result
                                        .getCoordinates().getLongitude());
                            }
                        }

                        @Override
                        public void onFailure(final PositionError reason) {
                            useCurrentLocationSwitch.setValue(false, true);
                            remove(useCurrentLocationSwitch);
                        }
                    });
        }
    }

    private Element getRowElement(final Widget field) {
        Element elem = field.getElement();
        while (!elem.getClassName().contains("v-touchkit-componentgroup-row")) {
            elem = elem.getParentElement();
        }
        return elem;
    }

    public final boolean validateFields() {
        resetValidations();

        ArrayList<Widget> invalidFields = new ArrayList<Widget>();

        boolean valid = true;
        if (!useCurrentLocationSwitch.getValue()
                && (addressField.getText() == null || addressField.getText()
                .trim().isEmpty())) {
            valid = false;
            invalidFields.add(addressField);
        }

        if (violationBox.getSelectedIndex() == 0) {
            valid = false;
            invalidFields.add(violationBox);
        }

        for (Widget invalidField : invalidFields) {
            getRowElement(invalidField).addClassName("invalid");
        }
        return valid;
    }

    public InformationLayout(final TicketViewModuleListener listener) {
        this.listener = listener;
        final String styleOn = "v-touchkit-switch-on";
        useCurrentLocationSwitch = new VSwitch();
        useCurrentLocationSwitch
                .addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                    @Override
                    public void onValueChange(
                            final ValueChangeEvent<Boolean> event) {
                        if (event.getValue()) {
                            useCurrentLocationSwitch.addStyleName(styleOn);
                            requestUserPosition();
                        } else {
                            useCurrentLocationSwitch.removeStyleName(styleOn);
                        }
                        if (!event.getValue()) {
                            currentPosition = null;
                        }

                        getRowElement(addressField).getStyle().setProperty(
                                "display", event.getValue() ? "none" : "");
                        getRowElement(areaBox).getStyle().setProperty(
                                "display", event.getValue() ? "none" : "");
                    }
                });
        final ChangeHandler ch = new ChangeHandler() {
            @Override
            public void onChange(final ChangeEvent event) {
                listener.fieldsChanged();
            }
        };

        violationBox = new ListBox();
        violationBox.addChangeHandler(ch);
        violationBox.addItem("Wybierz...", (String) null);
        for (Violation violation : Violation.values()) {
            violationBox.addItem(violation.getCaption(), violation.name());
        }
        violationBox.setWidth("100%");
        violationBox.setStyleName("v-select-select");
        SimplePanel violationWrapper = new SimplePanel(violationBox);
        violationWrapper.setStyleName("v-select");
        add(violationWrapper);
        updateCaption(violationWrapper, "Violation", null, "100.0%",
                "v-caption");

        add(useCurrentLocationSwitch);
        updateCaption(useCurrentLocationSwitch, "My location", null, "100.0%",
                "v-caption");

        final ValueChangeHandler<String> vch = new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(final ValueChangeEvent<String> event) {
                listener.fieldsChanged();
            }
        };

        addressField = new VTextField();
        addressField.addValueChangeHandler(vch);
        addressField.setWidth("100%");

        /*
         * ClickHandler is needed for fixing bug #14743 with WP
         */
        addressField.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                addressField.setFocus(true);
            }
        });

        add(addressField);
        updateCaption(addressField, "Address", null, "100.0%", "v-caption");



        areaBox = new ListBox();
        areaBox.addChangeHandler(ch);
        areaBox.addItem("Wybierz...", (String) null);
//        for (char zone : "ABC".toCharArray()) {
//            for (int i = 1; i < 5; i++) {
//                String area = String.valueOf(zone) + i;
//                areaBox.addItem(area, area);
//            }
//        }
        for (Regions regions : Regions.values()) {
            areaBox.addItem(regions.getCaption(), regions.name());
        }
        areaBox.setWidth("100%");
        areaBox.setStyleName("v-select-select");
        SimplePanel areaWrapper = new SimplePanel(areaBox);
        areaWrapper.setStyleName("v-select");
        add(areaWrapper);
        updateCaption(areaWrapper, "Area", null, "100.0%", "v-caption");

        useCurrentLocationSwitch.setValue(true, true);
    }

    public final void resetValidations() {
        for (Widget field : Arrays.<Widget>asList(addressField,
                violationBox, areaBox)) {
            getRowElement(field).removeClassName("invalid");
        }
    }

    public final void populateTicket(final Ticket ticket) {
        final Location location = new Location();
        if (currentPosition != null) {
            location.setLatitude(currentPosition.getCoordinates().getLatitude());
            location.setLongitude(currentPosition.getCoordinates()
                    .getLongitude());
        }
        location.setAddress(addressField.getText());
        ticket.setLocation(location);

        ticket.setTimeStamp(date);

        int vi = violationBox.getSelectedIndex();
        ticket.setViolation(vi == 0 ? null : Violation.values()[vi - 1]);

        ticket.setArea(areaBox.getValue(areaBox.getSelectedIndex()));
    }

    public final void ticketUpdated(final Ticket ticket) {
        addressField.setText(ticket.getLocation().getAddress());
        int vi = Arrays.asList(Violation.values()).indexOf(
                ticket.getViolation()) + 1;
        violationBox.setSelectedIndex(vi);

        areaBox.setSelectedIndex(0);
        for (int i = 0; i < areaBox.getItemCount(); i++) {
            if (areaBox.getValue(i).equals(ticket.getArea())) {
                areaBox.setSelectedIndex(i);
                break;
            }
        }
    }
}
