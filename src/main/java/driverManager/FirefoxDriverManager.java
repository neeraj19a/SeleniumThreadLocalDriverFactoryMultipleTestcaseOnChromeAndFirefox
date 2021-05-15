package driverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import utils.Log;

import java.io.File;


public class FirefoxDriverManager extends DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private static GeckoDriverService geckoDriverService;

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriverForThread() {
        driver.set(getWebdriver());
    }

    public static void closeDriverForThread() {
        driver.get().close();
        driver.remove();

    }
    private static synchronized WebDriver getWebdriver() {
        WebDriver webdriver;
        Log.info("Initializing Firefox driver");

        /*System.setProperty("webdriver.chrome.driver", getFirefoxDriverPath().toString());
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.setPlatform(Platform.ANY);
        final FirefoxOptions options = new FirefoxOptions()
                .setLogLevel(FirefoxDriverLogLevel.ERROR);

        capability.merge(options);
        webdriver = new FirefoxDriver(options);*/

        WebDriverManager.firefoxdriver().setup();
        webdriver=new FirefoxDriver();

        Log.info("FirefoxDriver created");

        Log.info("FirefoxDriver created and webdriver is-->"+webdriver);

        return webdriver;
    }

    public static File getFirefoxDriverPath() {
        File file = null;

        String os = System.getProperty("os.name").toLowerCase();
        Log.info("OS value-->" + os);
        String separator = System.getProperty("file.separator");

        if (os.toLowerCase().contains("windows")) {
            String firefoxPathwindows = System.getProperty("user.dir")
                    + separator + "src"
                    + separator + "test"
                    + separator + "java"
                    + separator + "resources"
                    + separator + "windows"
                    + separator + "geckodriver.exe";
            System.out.println("Setting Up Firefox On Windows on the path-->" + firefoxPathwindows);
            Log.info("Setting Up Firefox On Windows on the path-->" + firefoxPathwindows);
            System.setProperty("webdriver.gecko.driver", firefoxPathwindows);

            file = new File(firefoxPathwindows);

        } else if (os.toLowerCase().contains("mac")) {
            String firefoxPathmac = System.getProperty("user.dir")
                    + separator + "src"
                    + separator + "test"
                    + separator + "java"
                    + separator + "resources"
                    + separator + "mac"
                    + separator + "geckodriver";
            System.out.println("Setting Up Firefox On MAC on the path-->" + firefoxPathmac);
            Log.info("Setting Up Firefox On MAC on the path-->" + firefoxPathmac);
            System.setProperty("webdriver.gecko.driver", firefoxPathmac);

            file = new File(firefoxPathmac);
        } else if (os.toLowerCase().contains("nux")) {
            String firefoxPathUnix = System.getProperty("user.dir")
                    + separator + "src"
                    + separator + "test"
                    + separator + "java"
                    + separator + "resources"
                    + separator + "mac"
                    + separator + "geckodriver";
            System.out.println("Setting Up Firefox On Unix on the path-->" + firefoxPathUnix);
            Log.info("Setting Up Firefox On Unix on the path-->" + firefoxPathUnix);
            System.setProperty("webdriver.gecko.driver", firefoxPathUnix);

            file = new File(firefoxPathUnix);
        }
        return file;
    }
/*
    @Override
    public void startService() {

        //if (null == geckoDriverService) {
            try {
                geckoDriverService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(getFirefoxDriverPath())
                        .usingAnyFreePort()
                        .build();
                geckoDriverService.start();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            Log.info("GeckoDriverService Started");
        //}
    }

    @Override
    public void stopService() {

        if (null != geckoDriverService && geckoDriverService.isRunning()) {
            geckoDriverService.stop();
            Log.info("GeckoDriverService Stopped");
        }
    }*/

    @Override
    public WebDriver startWebDriverClient() {
        WebDriver driver;
        try {
            FirefoxDriverManager.setDriverForThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = new FirefoxDriverManager().getDriver();

        Log.info("Driver after StartWebdriverClient-->"+driver);
        return driver;
    }

}

