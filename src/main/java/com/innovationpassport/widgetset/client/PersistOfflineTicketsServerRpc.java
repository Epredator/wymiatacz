package com.innovationpassport.widgetset.client;

import java.util.List;

import com.innovationpassport.widgetset.client.model.Ticket;
import com.vaadin.shared.communication.ServerRpc;

public interface PersistOfflineTicketsServerRpc extends ServerRpc {

    void persistTickets(List<Ticket> tickets);

}
