package com.innovationpassport;

import java.util.List;

import com.vaadin.addon.touchkit.extensions.OfflineMode;
import com.innovationpassport.util.DataUtil;
import com.innovationpassport.widgetset.client.PersistOfflineTicketsServerRpc;
import com.innovationpassport.widgetset.client.model.Ticket;

/**
 * 
 * This is server side counter part for Parking offline extension. Here we
 * handle persisting the tickets stored during offline usage.
 * 
 */
public class ParkingOfflineModeExtension extends OfflineMode {

    private final PersistOfflineTicketsServerRpc serverRpc = new PersistOfflineTicketsServerRpc() {
        @Override
        public void persistTickets(final List<Ticket> tickets) {
            DataUtil.persistTickets(tickets);
        }
    };

    public ParkingOfflineModeExtension() {
        registerRpc(serverRpc);
    }

}
