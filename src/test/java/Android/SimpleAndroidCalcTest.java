package Android;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SimpleAndroidCalcTest {

    AndroidDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Set android deviceName desired capability. Set your device name.
        capabilities.setCapability("deviceName", "emulator-5554");

        // Set android platformName desired capability. It's Android in our case here.
        capabilities.setCapability("platformName", "Android");

        // Set android appPackage desired capability. It is
        // com.android.calculator2 for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appPackage", "com.android.calculator2");

        // Set android appActivity desired capability. It is
        // com.android.calculator2.Calculator for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    public void Sum() {

        // Click on number 2 button.
        driver.findElement(By.id("com.android.calculator2:id/digit_2")).click();

        // Click on + button.
        driver.findElement(By.id("com.android.calculator2:id/op_add")).click();

        // Click on number 5 button.
        driver.findElement(By.id("com.android.calculator2:id/digit_5")).click();

        // Click on = button.
        driver.findElement(By.id("com.android.calculator2:id/eq")).click();

        // Get result from result text box.
        String result = driver.findElement(By.id("com.android.calculator2:id/result")).getText();
        System.out.println("Number sum result is : " + result);
        Assert.assertEquals(result, "8");

    }

    @AfterTest
    public void End() {
        driver.quit();
    }
}
