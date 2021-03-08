import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FilesTest {

    private static final String APPIUM = "http://localhost:4723/wd/hub";
    private static final String ANDROID_PHOTO_PATH = "/run/user/1000/gvfs/mtp:host=%5Busb%3A003%2C017%5D/Phone/Pictures";

    private AndroidDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.google.android.apps.photos");
        caps.setCapability("appActivity", ".home.HomeActivity");
        caps.setCapability("noReset", true);
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPhotos() throws IOException {
        File image = new File("src/test/resources/appium_white_360.png").getAbsoluteFile();
        driver.pushFile(ANDROID_PHOTO_PATH + "/" + image.getName(), image);

    }
}
