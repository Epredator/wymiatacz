package com.innovationpassport.widgetset.client.ticketview;

public interface TicketViewModuleListener {
    void fieldsChanged();

    void positionReceived(double latitude, double longitude);
}
