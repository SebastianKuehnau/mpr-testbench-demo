import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.FormLayoutElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class AddressBookIT extends TestBenchTestCase {

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp() throws Exception {
        setDriver(new ChromeDriver());
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }


    @Test
    public void testAddressBook() {
        getDriver().get("http://localhost:8080/");
        Assert.assertTrue($(GridElement.class).exists());
    }

    @Test
    public void testFormShowsCorrectData(){
        getDriver().get("http://localhost:8080/");

        // 1. Find the Table
        GridElement table = $(GridElement.class).first();

        // 2. Store the first name and last name values shown
        // in the first row of the table for later comparison
        String firstName = table.getCell(0, 0).getText();
        String lastName = table.getCell(0, 1).getText();

        // 3. Click on the first row
        table.getCell(0, 0).click();

        // 4. Assert that the values in the first name and
        // last name fields are the same as in the table
        Assert.assertEquals(firstName, $(FormLayoutElement.class).$(TextFieldElement.class).first().getValue());
        Assert.assertEquals(lastName, $(FormLayoutElement.class).$(TextFieldElement.class).get(1).getValue());
    }

    @Test
    public void testEnterNew() {
        getDriver().get("http://localhost:8080/");

        // 1. Click the "New contact" button
        $(ButtonElement.class).caption("Add new customer").first().click();

        // 2. Enter "Tyler" into the first name field
        $(FormLayoutElement.class).$(TextFieldElement.class).
                first().setValue("Tyler");

        // 3. Enter "Durden" into the last name field
        $(FormLayoutElement.class).$(TextFieldElement.class).
                get(1).setValue("Durden");

        // 4. Save the new contact by clicking "Save" button
        $(ButtonElement.class).caption("Save").first().click();

        // 5. Click on some other row
        GridElement table = $(GridElement.class).first();
        table.getCell(6, 0).click();

        // 6. Assert that the entered name is not in the text
        // fields any longer
        Assert.assertNotEquals("Tyler", $(FormLayoutElement.class).
                $(TextFieldElement.class).first().getValue());
        Assert.assertNotEquals("Durden", $(FormLayoutElement.class).
                $(TextFieldElement.class).get(1).getValue());

        // 7. Click on the first row
        table.getCell(0,0).click();

        // 8. Verify that the first row and form
        // contain "Tyler Durden"
        Assert.assertEquals("Tyler",table.getCell(0, 0).getText());
        Assert.assertEquals("Durden",table.getCell(0, 1).getText());
        Assert.assertEquals("Tyler", $(FormLayoutElement.class).
                $(TextFieldElement.class).first().getValue());
        Assert.assertEquals("Durden", $(FormLayoutElement.class).
                $(TextFieldElement.class).get(1).getValue());
    }

    @Test
    public void testEnterNewPageObjects() {
        getDriver().get("http://localhost:8080/");

        AddressBook addressBook = new AddressBook(getDriver());

        EntryForm form = addressBook.createNewEntry();
        form.setFirstName("Tyler");
        form.setLastName("Durden");
        form.saveEntry();

        // Select some other entry
        form = addressBook.selectEntryAtIndex(6);

        // Assert that the entered name is not in the
        // text fields any longer
        Assert.assertNotEquals("Tyler", form.getFirstName());
        Assert.assertNotEquals("Durden", form.getLastName());

        // Verify that the first row and form contain
        // "Tyler Durden"
        form = addressBook.selectEntryAtIndex(0);
        Assert.assertEquals("Tyler", addressBook.getFirstNameAtIndex(0));
        Assert.assertEquals("Durden", addressBook.getLastNameAtIndex(0));
        Assert.assertEquals("Tyler", form.getFirstName());
        Assert.assertEquals("Durden", form.getLastName());
    }
}