package driverManager;

public class DriverManagerFactory {

    public synchronized DriverManager getManager(String browser) {

        DriverManager driverManager = null;

        switch (browser.toUpperCase()) {
            case "CHROME":
                driverManager = new ChromeDriverManager();
                break;
            case "FIREFOX":
                //driverManager = new FirefoxDriverManager();
                break;
        }

        return driverManager;

    }
}