package org.vaadin.example;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.annotations.Theme;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.mpr.LegacyWrapper;
import com.vaadin.mpr.core.HasLegacyComponents;
import com.vaadin.mpr.core.MprTheme;

import java.io.File;
import java.io.IOException;

@MprTheme("mytheme")
@Route("spreadsheet")
public class SpreadsheetView extends VerticalLayout {

    private Spreadsheet spreadsheet = null;

    public SpreadsheetView() {
        add(new Span("Spreadsheet View"));

        initSpreadsheet();
        LegacyWrapper legacyWrapper = new LegacyWrapper(spreadsheet);
        legacyWrapper.setSizeFull();
        add(legacyWrapper);

        setSizeFull();
    }

    private void initSpreadsheet(){
        File sampleFile = new File("Simple Invoice.xlsx");
        try {
            spreadsheet = new Spreadsheet(sampleFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
