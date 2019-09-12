package Android;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MyStepdefs {

    AppiumDriver<AndroidElement> driver;

    @Before
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
//        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appPackage", "com.google.android.apps.nexuslauncher");

        // Set android appActivity desired capability. It is
        // com.android.calculator2.Calculator for calculator application.
        // Set your application's appPackage if you are using any other app.
//        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
        capabilities.setCapability("appActivity", "com.google.android.apps.nexuslauncher.NexusLauncherActivity");


        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.
        driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @After
    public void End() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();
    }

    @Given("^The calculator app is running$")
    public void theCalculatorAppIsRunning(){
        // Here I'm checking whether a specific identifier of the calculator app is displayed or not
        // If it's displayed a useful line will be printed in the console and the next step will be performed
        // If it's not displayed a useful line will be printed in the console and the test will fail
        Activity calculator = new Activity("com.android.calculator2", "com.android.calculator2.Calculator");
        ((AndroidDriver) driver).startActivity(calculator);
        boolean calculatorRunning;
        try {
            driver.findElement(By.id("com.android.calculator2:id/formula")).isDisplayed();
            calculatorRunning = true;
        } catch (Exception e){
            calculatorRunning = false;
        }
        if (calculatorRunning){
            System.out.println("The calculator app is running");
        } else {
            System.out.println("The calculator app is currently not running");
        }
    }

    @When("^I add (\\d+) and (\\d+)$")
    public void iAddAnd(int digit1, int digit2){
        // Click on number button which has been defined as the first variable.
        driver.findElement(By.id("com.android.calculator2:id/digit_" + digit1)).click();

        // Click on + button.
        driver.findElement(By.id("com.android.calculator2:id/op_add")).click();

        // Click on number button which has been defined as the second variable.
        driver.findElement(By.id("com.android.calculator2:id/digit_" + digit2 + "")).click();

    }

    @And("^I press the equals button$")
    public void iPressTheEqualsButton() {
        // Click on = button.
        driver.findElement(By.id("com.android.calculator2:id/eq")).click();
    }

    @Then("I can see the result is {string}")
    public void iCanSeeTheResultIs(String result) {
        // Get result from result text box and verify if it matches the number which was defined as the variable
        String resultCalculator = driver.findElement(By.id("com.android.calculator2:id/result")).getText();
        Assert.assertEquals(resultCalculator, result);
    }

    public static Activity appSettings (String appName){
        String appPackage = "";
        String appActivity = "";
        if (appName.toLowerCase().equals("calculator")) {
            appPackage = "com.android.calculator2";
            appActivity = "com.android.calculator2.Calculator";
        } else if (appName.toLowerCase().equals("selendroid")){
            appPackage = "io.selendroid.testapp";
            appActivity = "io.selendroid.testapp.HomeScreenActivity";
        }
        Activity application = new Activity(appPackage, appActivity);
        return application;
    }

    @Given("the app: {string} is active")
    public void theAppIsActive(String appName) {
        Activity applicatie = appSettings(appName);
        ((AndroidDriver) driver).startActivity(applicatie);
    }

    @When("I click the EN button")
    public void iClickTheENButton() {
        driver.findElement(By.id("io.selendroid.testapp:id/buttonTest")).click();
    }

    @Then("A popup with the text {string} is visible")
    public void aPopupWithTheTextIsVisible(String text) {
        String popup = driver.findElement(By.id("android:id/message")).getText();
        System.out.println(text);
        System.out.println(popup);
        Assert.assertEquals(popup, text);
    }

    @When("I type {string} in a form field")
    public void iTypeInAFormField(String text) {
        driver.findElement(By.id("io.selendroid.testapp:id/my_text_field")).sendKeys(text);
    }

    @Then("I see the text {string} on the screen")
    public void iSeeTheTextOnTheScreen(String text) {
        String textinform = driver.findElement(By.id("io.selendroid.testapp:id/my_text_field")).getText();
        Assert.assertEquals(text, textinform);
    }

    @When("I click on the formButton")
    public void iClickOnTheFormButton() {
        driver.findElement(By.id("io.selendroid.testapp:id/startUserRegistration")).click();
    }

    @And("I fill in {string} in the {string}")
    public void iFillInInThe(String value, String element) {
      AndroidElement inputField = new AndroidElement();
        if (element.equals("UsernameField")){
            inputField = driver.findElement(By.id("io.selendroid.testapp:id/inputUsername"));
        } else if (element.equals("emailField")) {
            inputField = driver.findElement(By.id("io.selendroid.testapp:id/inputEmail"));
        } else if (element.equals("passwordField")) {
            inputField = driver.findElement(By.id("io.selendroid.testapp:id/inputPassword"));
        } else if (element.equals("nameField")) {
            inputField = driver.findElement(By.id("io.selendroid.testapp:id/inputName"));
            inputField.clear();
        }

        inputField.sendKeys(value);
    }
}
