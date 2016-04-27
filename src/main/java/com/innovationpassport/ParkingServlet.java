package com.innovationpassport;

import javax.servlet.ServletException;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.TouchKitSettings;

public class ParkingServlet extends TouchKitServlet {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        TouchKitSettings s = getTouchKitSettings();

        String contextPath = getServletConfig().getServletContext()
                .getContextPath();
        s.getApplicationIcons().addApplicationIcon(
                contextPath + "/VAADIN/themes/wymiatacz/icon.png");

        s.getApplicationCacheSettings().setCacheManifestEnabled(true);

    }
}
