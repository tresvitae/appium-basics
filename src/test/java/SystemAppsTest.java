import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static java.time.Duration.ofSeconds;

public class SystemAppsTest {

    private static final String APPIUM = "http://localhost:4723/wd/hub";
    private AppiumDriver driver;

    private void setUpAndroid() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");

        // Set System app for Android
        caps.setCapability("appPackage", "com.android.settings");
        caps.setCapability("appActivity",".Settings");
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    private void setUpIOS() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "12.0");
        caps.setCapability("deviceName", "iPhone X");

        // Set System app for iOS
        caps.setCapability("app", "com.apple.Preferences");
        driver = new IOSDriver(new URL(APPIUM), caps);
    }

    @Before
    public void setUp() throws Exception {
        setUpAndroid();
//        setUpIOS();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void clickOptimizeNowButtonAndroid()  {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.findElementByAccessibilityId("Search").click();
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.android.settings.intelligence:id/search_src_text"))).sendKeys("Device care");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[3]/android.widget.LinearLayout/android.widget.TextView"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.samsung.android.lool:id/clean_up_btn"))).click();
    }
}
