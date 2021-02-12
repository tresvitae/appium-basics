import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import jdk.jshell.spi.ExecutionControlProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class ElementsBasicTest {
    private static final String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.apk";
    private static final String APPIUM = "http://localhost:4723/wd/hub";

    private AndroidDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("app", APP);
        // start up the app using appium
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        //EXPLICIT WAITS
        // construct a reusable wait object, max 10 sec
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // waits until find the expected element for 10 sec and store variable
        WebElement screen = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Login Screen")));

        //click() to tap
        screen.click();

        WebElement username = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("username")));
        //sendKeys() to enter text input field
        username.sendKeys("alice");

        // no need to use 'wait' assertion due to Activity already displayed
        WebElement password = driver.findElement(MobileBy.AccessibilityId("password"));
        password.sendKeys("mypassword");

        WebElement login = driver.findElement(MobileBy.AccessibilityId("loginBtn"));
        login.click();

        //prove successfully logged in
        WebElement loginText = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//android.widget.TextView[contains(@text, 'You are logged in')]")));
        //assertion to check if string 'alice' exists, using getText() to retrieve the text displayed in a field or label
        assert(loginText.getText().contains("alice"));
    }
}
