import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Config;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.VoidDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebDriverManagerFactory {
    static private final Logger LOGGER = LoggerFactory.getLogger(WebDriverManagerFactory.class);

    private WebDriverManagerFactory() {
        // do not instantiate
    }

    /**
     * Return a new WebDriverManager instance matching the specified name, version, and driver.
     *
     * <p>
     *     This method uses WebDriverManager, which allows us to download and
     *     set the browser driver binaries without
     *     having to manually put them in automation scripts.
     *     {@see https://bonigarcia.dev/webdrivermanager/} and
     *     {@see https://www.toolsqa.com/selenium-webdriver/webdrivermanager/}.
     * </p>
     *
     * @param webDriverName the name of the web driver - must match one of DriverManagerType's values.
     * @param browserVersion the version of the browser to use.
     * @param driverVersion the version of the driver to use for the browser.
     *
     * @return the requested WebDriverManager
     */
    public static WebDriverManager getInstanceFor(String webDriverName, String browserVersion, String driverVersion) {
        LOGGER.info("WebDriverManager requested for browser: {}", webDriverName);

        // find matching DriverManagerType
        DriverManagerType driverManagerType = DriverManagerType.valueOf(webDriverName.toUpperCase());
        LOGGER.debug("Matching DriverManagerType browser name: {}", driverManagerType.getBrowserName());

        // get matching WebDriverManager with requested browser and driver versions
        WebDriverManager webDriverManager = WebDriverManager.getInstance(driverManagerType);
        LOGGER.debug("Instantiated WebDriverManager: {}", webDriverManager.getClass().getCanonicalName());
        if (browserVersion != null) {
            webDriverManager.browserVersion(browserVersion);
        }
        if (driverVersion != null) {
            webDriverManager.driverVersion(driverVersion);
        }

        // download driver if not present
        webDriverManager.setup();

        if (LOGGER.isDebugEnabled()) {
            logWebDriverManager(webDriverManager);
        }

        return webDriverManager;
    }

    /**
     * Return a new WebDriverManager instance matching the specified name and version.
     *
     * @return the requested WebDriverManager
     */
    public static WebDriverManager getInstanceFor(String webDriverName, String browserVersion) {
        return getInstanceFor(webDriverName, browserVersion, null);
    }

    /**
     * Return a new WebDriverManager instance matching the specified name.
     *
     * @return the requested WebDriverManager
     */
    public static WebDriverManager getInstanceFor(String webDriverName) {
        return getInstanceFor(webDriverName, null, null);
    }

    /**
     * Return a new WebDriverManager instance matching the specified name and driver.
     *
     * @return the requested WebDriverManager
     */
    public static WebDriverManager getInstanceByDriverVersion(String webDriverName, String driverVersion) {
        return getInstanceFor(webDriverName, null, driverVersion);
    }

    private static void logWebDriverManager(WebDriverManager webDriverManager) {
        DriverManagerType driverManagerType = webDriverManager.getDriverManagerType();

        // log WebDriver canonical class name corresponding to DriverManagerType
        LOGGER.debug("Corresponding WebDriver: {}", driverManagerType.browserClass());

        if (webDriverManager.getClass() == VoidDriverManager.class) {
            return;
        }

        LOGGER.debug("[webDriverManager]");
        LOGGER.debug("    DriverVersions:          {}", webDriverManager.getDriverVersions());
        LOGGER.debug("    DownloadedDriverPath:    {}", webDriverManager.getDownloadedDriverPath());
        LOGGER.debug("    DownloadedDriverVersion: {}", webDriverManager.getDownloadedDriverVersion());

        Config config = webDriverManager.config();
        LOGGER.debug("    [config]");
        LOGGER.debug("        getOperatingSystem: {}", config.getOperatingSystem());
        switch (driverManagerType) {
            case CHROMIUM:
                LOGGER.debug("        DriverSnapPath:     {}", config.getChromiumDriverSnapPath());
                LOGGER.debug("        DriverVersion:      {}", config.getChromiumDriverVersion());
                LOGGER.debug("        BrowserVersion:     {}", config.getChromiumVersion());
                break;
            case CHROME:
                LOGGER.debug("        DownloadUrlPattern: {}", config.getChromeDownloadUrlPattern());
                LOGGER.debug("        DriverExport:       {}", config.getChromeDriverExport());
                LOGGER.debug("        DriverUrl:          {}", config.getChromeDriverUrl());
                LOGGER.debug("        DriverMirrorUrl:    {}", config.getChromeDriverMirrorUrl());
                LOGGER.debug("        DriverVersion:      {}", config.getChromeDriverVersion());
                LOGGER.debug("        BrowserVersion:     {}", config.getChromeVersion());
                break;
            case EDGE:
                LOGGER.debug("        DownloadUrlPattern: {}", config.getEdgeDownloadUrlPattern());
                LOGGER.debug("        DriverExport:       {}", config.getEdgeDriverExport());
                LOGGER.debug("        DriverUrl:          {}", config.getEdgeDriverUrl());
                LOGGER.debug("        DriverVersion:      {}", config.getEdgeDriverVersion());
                LOGGER.debug("        BrowserVersion:     {}", config.getEdgeVersion());
                break;
            case FIREFOX:
                LOGGER.debug("        DriverExport:       {}", config.getFirefoxDriverExport());
                LOGGER.debug("        DriverUrl:          {}", config.getFirefoxDriverUrl());
                LOGGER.debug("        DriverMirrorUrl:    {}", config.getFirefoxDriverMirrorUrl());
                LOGGER.debug("        BrowserVersion:     {}", config.getFirefoxVersion());
                break;
            case OPERA:
                LOGGER.debug("        DriverExport:       {}", config.getOperaDriverExport());
                LOGGER.debug("        DriverUrl:          {}", config.getOperaDriverUrl());
                LOGGER.debug("        DriverMirrorUrl:    {}", config.getOperaDriverMirrorUrl());
                LOGGER.debug("        DriverVersion:      {}", config.getOperaDriverVersion());
                LOGGER.debug("        BrowserVersion:     {}", config.getOperaVersion());
                break;
            case IEXPLORER:
            case SAFARI:
                break;
        }
    }
}
