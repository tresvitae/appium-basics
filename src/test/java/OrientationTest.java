import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class OrientationTest {

    private static final String APP_ANDROID = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.apk";
    private static final String APPIUM = "http://localhost:4723/wd/hub";

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("app", APP_ANDROID);
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testScreenMethods() throws IOException {
        ScreenOrientation curOrientation = driver.getOrientation();
        System.out.println(curOrientation);
        if (curOrientation != ScreenOrientation.LANDSCAPE){
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }

        // Prints dimension of screen
        Dimension size = driver.manage().window().getSize();
        System.out.println(size);

        // Take a screen file and save it as screen.png
        File screenFile = driver.getScreenshotAs(OutputType.FILE);
        File saveFile = new File("/home/tresvitae/IdeaProjects/screen.png");
        FileUtils.copyFile(screenFile, saveFile);
    }
}