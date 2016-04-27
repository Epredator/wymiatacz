package com.innovationpassport.ui;

import java.util.List;

import com.innovationpassport.MainUI;
import com.innovationpassport.util.DataUtil;
import com.innovationpassport.widgetset.client.model.Ticket;
import com.innovationpassport.widgetset.client.TicketViewServerRpc;
import com.innovationpassport.widgetset.client.TicketViewState;
import com.vaadin.ui.AbstractComponent;

/**
 * A view to report a new parking ticket.
 * <p>
 * The form in the view uses "pre created fields pattern" thus the view also
 * implements FormFieldFactory that return pre created fields based on the
 * property key.
 */
public class TicketView extends AbstractComponent implements
        TicketViewServerRpc {

    public TicketView() {
        setSizeFull();
        registerRpc(this);
    }

    @Override
    protected TicketViewState getState() {
        return (TicketViewState) super.getState();
    }

    @Override
    public void persistTickets(final List<Ticket> tickets) {
        DataUtil.persistTickets(tickets);
        getState().setTicket(new Ticket());
    }

    @Override
    public void positionReceived(final double latitude, final double longitude) {
        MainUI.getApp().setCurrentLatitude(latitude);
        MainUI.getApp().setCurrentLongitude(longitude);
    }

    @Override
    public void updateState(final Ticket ticket) {
        getState().setTicket(ticket);
    }
}
