package base;

import driverManager.ChromeDriverManager;
import driverManager.DriverManager;
import driverManager.FirefoxDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pages.PageGenerator;
import utils.Log;
import utils.PropertyManager;
import utils.TestUtil;
import utils.WebEventListener;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public PageGenerator page;
    public WebDriver driver;
    DriverManager driverManager;
    private EventFiringWebDriver e_driver;
    private WebEventListener eventListener;


    /*@Parameters("browser")
    @BeforeTest
    public synchronized void startService(String browser) {
        Log.info("Test is starting on -->" + browser);
    }
*/
   /* @Parameters("browser")
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context,String browser) {

        Log.info("In Before suite");
        driverManager = new DriverManagerFactory().getManager(browser);
        Log.info("Starting driver for " + browser + " browser");
        //driverManager.startService();
    }*/

    @Parameters("browser")
    @AfterSuite(alwaysRun = true)
    public void afterSuite(String browser) {

        Log.info("In After suite");
        Log.info("Stopping driver for " + browser + " browser");
        //driverManager.stopService();

    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(String browser) {

        Log.info("In Before Method");
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("Running on " + os + " Operating System");
        Log.info("Running on " + os + " Operating System");
        Log.info("Initializing driver for " + browser + " browser");

        if (browser.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriverManager().startWebDriverClient();
            Log.info("Chrome Driver value is -->"+driver);
        } else if (browser.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriverManager().startWebDriverClient();
            Log.info("Firefox Driver value is -->"+driver);
        }
        e_driver = new EventFiringWebDriver(driver);
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        driver.get(PropertyManager.getinstance().getProperty("url"));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
        page = new PageGenerator(driver);

    }

    @Parameters("browser")
    @Step("Taking Screenshot")
    @Attachment
    public synchronized byte[] screenShot(String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            Log.info("Chrome-->"+ChromeDriverManager.getDriver());
            return    ((TakesScreenshot) ChromeDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        } else if (browser.equalsIgnoreCase("Firefox")) {

            Log.info("Firefox-->"+FirefoxDriverManager.getDriver());
            return ((TakesScreenshot) FirefoxDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

        }
        return null;
    }

    @Parameters("browser")
    @Step("Closing Browser")
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, String browser) throws InterruptedException {
        //WebDriver driver = null;
        Log.info("In After Method");
        {
            if (result.getStatus() == ITestResult.FAILURE) {
                Log.info("=========Test Case " + result.getName() + " Failed=======");
                Log.info("Taking Screenshot");
                screenShot(browser);
            } else {
                Log.info("In After Method, Test " + result.getName() + " is Ending");
                Log.info("Taking Screenshot");
                screenShot(browser);
            }
            if (browser.equalsIgnoreCase("Chrome")) {
                ChromeDriverManager.closeDriverForThread();
            } else if (browser.equalsIgnoreCase("Firefox")) {
                FirefoxDriverManager.closeDriverForThread();

            }
        }
    }

}