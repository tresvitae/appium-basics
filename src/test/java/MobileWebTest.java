import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.sql.Time;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MobileWebTest {

    private static final String APPIUM = "http://localhost:4723/wd/hub";

    // Class for exploring website
    private WebDriver driver;

    @Before
    public void setUpAndroid() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        // CHROMEDRIVER NEED TO BE SETUP DURING OPEN
        // appium --chromedriver-executable /usr/local/bin/chromedriver
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
    public void checkTitle() throws InterruptedException{
        driver.get("https://www.linux.pl");

        String titlePage = driver.getTitle();
        Assert.assertEquals("Polska Strona Linuksa - Linux.pl", titlePage);
    }

    @Test
    public void openContacts() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.get("https://www.linux.pl");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"page\"]/div/header/div[1]/div[2]/div[1]/span"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"menu-item-3105\"]/a"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"menu-item-2015\"]/a"))).click();

        Thread.sleep(3000);
        String comapnyAddress = driver.findElement(By.xpath("//*[@id=\"primary\"]/div[2]/div/div/div/div[4]/div[2]/p/strong")).getText();
        Assert.assertEquals("AB-Com Arkadiusz Bednarczyk", comapnyAddress);
    }
    @Test
    public void changeToDarkMode() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.get("https://www.linux.pl");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"page\"]/div/header/div[1]/div[2]/div[3]/span[1]/i[2]"))).click();
    }
}
