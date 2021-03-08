import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.GsmCallActions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PhoneSMSTest {

    //Phone call and SMS accepted ony on Androids Emulators
    private static final String APP_ANDROID = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.apk";
    private static final String APPIUM = "http://localhost:4723/wd/hub";
    private static final String PHONE_NUMBER = "5551237890";

    private By VERIFY_SCREEN = MobileBy.AccessibilityId("Verify Phone Number");
    private By SMS_WAITING = MobileBy.xpath("//android.widget.TextView[contains(@text, 'Waiting to receive')]");
    private By SMS_VERIFIED = MobileBy.xpath("//android.widget.TextView[contains(@text, 'verified')]");

    private static final String DEVICE_ID = "emulator-5554";

    private AndroidDriver driver;

    WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", APP_ANDROID);
        caps.setCapability("udid", DEVICE_ID);

        // set add capability to read calls
        caps.setCapability("autoGrantPermissions", true);
        driver = new AndroidDriver(new URL(APPIUM), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver,5);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPhone() throws InterruptedException{
        driver.findElementByAccessibilityId("Login Screen").click();
        // trigger an incoming call
        driver.makeGsmCall(PHONE_NUMBER, GsmCallActions.CALL);
        Thread.sleep(2000);
        driver.makeGsmCall(PHONE_NUMBER, GsmCallActions.ACCEPT);
        driver.findElementByAccessibilityId("username").sendKeys("hi");
        Thread.sleep(2000);
        driver.makeGsmCall(PHONE_NUMBER, GsmCallActions.CANCEL);
        driver.navigate().back();
    }

    @Test
    public void testSMS(){
        // send an sms message
        wait.until(ExpectedConditions.presenceOfElementLocated(VERIFY_SCREEN)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(SMS_WAITING));
        driver.sendSMS(PHONE_NUMBER, "123456789");
        wait.until(ExpectedConditions.presenceOfElementLocated(SMS_VERIFIED));
    }
}
