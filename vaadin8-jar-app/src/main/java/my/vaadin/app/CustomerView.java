package my.vaadin.app;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class CustomerView extends VerticalLayout implements View {

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>();
    private TextField filterText = new TextField();
    private CustomerForm form = new CustomerForm(this);

    public CustomerView() {

        filterText.setPlaceholder("filter by name...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);

        grid.addColumn(Customer::getFirstName).setCaption("First Name");
        grid.addColumn(Customer::getLastName).setCaption("Last Name");
        grid.addColumn(Customer::getEmail).setCaption("Email");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        addComponents(toolbar, main);

        // fetch list of Customers from service and assign it to Grid
        updateList();

        form.setVisible(false);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setCustomer(event.getValue());
            }
        });
    }

    public void updateList() {
        List<Customer> customers = service.findAll(filterText.getValue());
        grid.setItems(customers);
    }
}
