import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WebviewAutomationTest {
    private static final String APP_ANDROID = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.apk";
    private static final String APP_IOS = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.app.zip";
    private static final String APPIUM = "http://localhost:4723/wd/hub";

    private static final String APPIUM_PRO = "https://appiumpro.com";
    private static final String OTHER_SITE = "https://onet.pl";

    private static final By WEBVIEW_PAGE = MobileBy.AccessibilityId("Webview Demo");
    private static final By URL_FIELD = By.xpath("//android.widget.EditText[@content-desc=\"urlInput\"]");
    private static final By GO_BUTTON = MobileBy.AccessibilityId("navigateBtn");

    private AppiumDriver driver;

    private void setUpAndroid() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", APP_ANDROID);
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    private void setUpIOS() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "12.0");
        caps.setCapability("deviceName", "iPhone X");
        caps.setCapability("app", APP_IOS);
        driver = new IOSDriver(new URL(APPIUM), caps);
    }

    @Before
    public void setUp() throws Exception {
        setUpAndroid();
//        setUpIOS();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Nullable
    private String getWebContext(AppiumDriver driver) {
        ArrayList<String> contexts = new ArrayList(driver.getContextHandles());
        for (String context : contexts) {
            if (!context.equals("NATIVE_APP")) {
                return context;
            }
        }
        return null;
    }

    @Test
    public void testHybridApp() throws InterruptedException {
        driver.findElement(WEBVIEW_PAGE).click();

        driver.findElement(URL_FIELD).sendKeys(OTHER_SITE);
        driver.findElement(GO_BUTTON).click();

        // Assert that an error message pops up
        Alert alert = driver.switchTo().alert();
        assert alert.getText().contains("Sorry");
        alert.accept();

        // Assert that the webview did not actually go anywhere
        driver.context(getWebContext(driver));
        String bodyText = driver.findElement(By.cssSelector("body")).getText();
        assert bodyText.equals("Please navigate to a webpage");

        // Attempt to navigate to the correct site
        driver.context("NATIVE_APP");
        WebElement urlField = driver.findElement(URL_FIELD);
        urlField.click();
        urlField.sendKeys(APPIUM_PRO);
        driver.findElement(GO_BUTTON).click();

        // Assert that the webview went to the right place
        driver.context(getWebContext(driver));
        new WebDriverWait(driver,5).until(ExpectedConditions.titleContains("Appium Pro"));
    }
}