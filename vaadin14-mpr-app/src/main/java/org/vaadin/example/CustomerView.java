package org.vaadin.example;

import com.vaadin.flow.router.Route;
import com.vaadin.mpr.MprRouteAdapter;

@Route("customer-view")
public class CustomerView extends MprRouteAdapter<my.vaadin.app.CustomerView> {

    public CustomerView() {
        setSizeFull();
    }
}
