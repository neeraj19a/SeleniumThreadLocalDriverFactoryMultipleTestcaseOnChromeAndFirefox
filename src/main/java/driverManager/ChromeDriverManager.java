package driverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Log;

import java.io.File;


public class ChromeDriverManager extends DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private static ChromeDriverService chromeDriverService;

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
        Log.info("Initializing Chrome driver");

        //System.setProperty("webdriver.chrome.driver", getChromeDriverPath().toString());
        WebDriverManager.chromedriver().setup();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions cOptions = new ChromeOptions();

        /*cOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        cOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        */
        /*cOptions.addArguments("--disable-infobars");
        cOptions.addArguments("--disable-extensions");
        cOptions.addArguments("--disable-notifications");
        cOptions.addArguments("--disable-session-crashed-bubble");
        cOptions.addArguments("--disable-save-password-bubble");
        cOptions.addArguments("test-type");
        cOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        cOptions.setExperimentalOption("useAutomationExtension", false);

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        cOptions.setExperimentalOption("prefs", prefs);
        *//*capabilities.setPlatform(Platform.ANY);
        capabilities.setCapability(ChromeOptions.CAPABILITY, cOptions);*/

        webdriver = new ChromeDriver();
        Log.info("ChromeDriver created and webdriver is-->" + webdriver);

        return webdriver;
    }


    public static File getChromeDriverPath() {
        File file = null;

        String os = System.getProperty("os.name").toLowerCase();
        Log.info("OS value-->" + os);
        String separator = System.getProperty("file.separator");

        if (os.toLowerCase().contains("windows")) {
            String chromePathwindows = System.getProperty("user.dir")
                    + separator + "src"
                    + separator + "test"
                    + separator + "java"
                    + separator + "resources"
                    + separator + "windows"
                    + separator + "chromedriver.exe";
            System.out.println("Setting Up Chrome On Windows on the path-->" + chromePathwindows);
            Log.info("Setting Up Chrome On Windows on the path-->" + chromePathwindows);
            System.setProperty("webdriver.chrome.driver", chromePathwindows);

            file = new File(chromePathwindows);

        } else if (os.toLowerCase().contains("mac")) {
            String chromePathmac = System.getProperty("user.dir")
                    + separator + "src"
                    + separator + "test"
                    + separator + "java"
                    + separator + "resources"
                    + separator + "mac"
                    + separator + "chromedriver";
            System.out.println("Setting Up Chrome On MAC on the path-->" + chromePathmac);
            Log.info("Setting Up Chrome On MAC on the path-->" + chromePathmac);
            System.setProperty("webdriver.chrome.driver", chromePathmac);

            file = new File(chromePathmac);
        } else if (os.toLowerCase().contains("nux")) {
            String chromePathUnix = System.getProperty("user.dir")
                    + separator + "src"
                    + separator + "test"
                    + separator + "java"
                    + separator + "resources"
                    + separator + "mac"
                    + separator + "chromedriver";
            System.out.println("Setting Up Chrome On Unix on the path-->" + chromePathUnix);
            Log.info("Setting Up Chrome On Unix on the path-->" + chromePathUnix);
            System.setProperty("webdriver.chrome.driver", chromePathUnix);

            file = new File(chromePathUnix);
        }
        return file;
    }

    /*public void startService() {

        if (null == chromeDriverService) {
            Log.info("Initializing chrome driver service");
            chromeDriverService = new ChromeDriverService.Builder().usingDriverExecutable(getChromeDriverPath())
                    .usingAnyFreePort().build();
        }

        if (!chromeDriverService.isRunning()) {
            Log.info("Starting chrome driver service");
            try {
                chromeDriverService.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.info("Chrome driver service started");
        }
    }

    public void stopService() {

        if (null != chromeDriverService && chromeDriverService.isRunning()) {
            Log.info("Stopping chrome driver service");
            chromeDriverService.stop();
        }
    }*/


    @Override
    public WebDriver startWebDriverClient() {
        WebDriver driver;
        try {
            ChromeDriverManager.setDriverForThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = new ChromeDriverManager().getDriver();
        Log.info("Driver after StartWebdriverClient-->"+driver);
        return driver;
    }
}


