package org.vaadin.example.testbenchexample;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

/**
 * Base class for all our tests, allowing us to change the applicable driver,
 * test URL or other configurations in one place.
 */
public abstract class AbstractIT extends TestBenchTestCase {

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this,
            true);

    @Before
    public void setUp() throws Exception {
        setDriver(new ChromeDriver());
        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":8080/calc");
    }

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @After
    public void tearDown() {
        getDriver().quit();
    }
}
