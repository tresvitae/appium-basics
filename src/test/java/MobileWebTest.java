import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class MobileWebTest {

    private static final String APPIUM = "http://localhost:4723/wd/hub";

    // Class for exploring website
    private RemoteWebDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        // Set Browser name for testing
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        // start up the app using appium
        driver = new RemoteWebDriver(new URL(APPIUM), capabilities);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Navigate to a URL in browser
        driver.get("https://www.onet.pl");
        // Web specific locator strategies to click on menu.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("bubleMenuHambuerger"))).click();
        // locator strategy for web testing
        driver.findElement(By.linkText("xxxxxxxxx")).click();
        // Get the title of a webpage.
        driver.getTitle();

    }
}
