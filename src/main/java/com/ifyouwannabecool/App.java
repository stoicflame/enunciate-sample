package com.ifyouwannabecool;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;

/**
 * @author Ryan Heaton
 */
public class App extends ResourceConfig {

    public App() {
        packages(App.class.getPackage().getName());
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}
