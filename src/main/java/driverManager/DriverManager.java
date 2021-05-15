package driverManager;

import org.openqa.selenium.WebDriver;

/*
 * This class provides instance of WebDriver
 */
public abstract class DriverManager {

    public ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();



    public abstract WebDriver startWebDriverClient();


}



