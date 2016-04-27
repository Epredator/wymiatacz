package com.innovationpassport.widgetset.client;

public interface TicketViewModuleListener {
    void fieldsChanged();

    void positionReceived(double latitude, double longitude);
}
