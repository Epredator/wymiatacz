package com.innovationpassport.widgetset.client;

import com.innovationpassport.widgetset.client.model.Ticket;
import com.vaadin.shared.AbstractComponentState;

public class TicketViewState extends AbstractComponentState {
    private Ticket ticket = new Ticket();

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
