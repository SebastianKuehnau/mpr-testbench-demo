package org.vaadin.example.testbenchexample;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
@Route("calc")
@PageTitle("Calculator example")
public class CalcView extends HorizontalLayout {

    public CalcView() {
        setId("calc-view");
        setSpacing(true);
        setHeight("300px");
        org.vaadin.example.testbenchexample.Log log = new org.vaadin.example.testbenchexample.Log();
        log.setId("log");
        org.vaadin.example.testbenchexample.Keypad keypad = new org.vaadin.example.testbenchexample.Keypad(log);
        add(keypad, log);
    }

}
