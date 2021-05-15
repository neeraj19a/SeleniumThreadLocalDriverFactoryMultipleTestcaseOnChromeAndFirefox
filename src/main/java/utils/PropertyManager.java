package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static PropertyManager obj = null; //Lazy Initialization

    private PropertyManager() {
    }

    public static synchronized PropertyManager getinstance() {
        if (obj == null) {
            obj = new PropertyManager();
        }
        return obj;
    }

    public String getProperty(String property) {
        String value = "";
        Properties prop = new Properties();
        InputStream input = null;

        try {
            String os = System.getProperty("os.name").toLowerCase();
            Log.info("OS value-->" + os);
            String separator = System.getProperty("file.separator");

            File file = null;
            if (os.toLowerCase().contains("windows")) {

                String configPathwindows = System.getProperty("user.dir")
                        + separator + "src"
                        + separator + "main"
                        + separator + "java"
                        + separator + "configs"
                        + separator + "configuration.properties";

                System.out.println("Finding configuration.properties On Windows on the path-->" + configPathwindows);
                Log.info("Finding configuration.properties On Windows on the path-->" + configPathwindows);

                file = new File(configPathwindows);

            } else if (os.toLowerCase().contains("mac")) {
                String chromePathmac = System.getProperty("user.dir")
                        + separator + "src"
                        + separator + "main"
                        + separator + "java"
                        + separator + "configs"
                        + separator + "configuration.properties";

                System.out.println("Finding configuration.properties On MAC on the path-->" + chromePathmac);
                Log.info("Finding configuration.properties On MAC on the path-->" + chromePathmac);

                file = new File(chromePathmac);
            } else if (os.toLowerCase().contains("nux")) {
                String chromePathUnix = System.getProperty("user.dir")
                        + separator + "src"
                        + separator + "main"
                        + separator + "java"
                        + separator + "configs"
                        + separator + "configuration.properties";

                System.out.println("Finding configuration.properties On Unix on the path-->" + chromePathUnix);
                Log.info("Finding configuration.properties On Unix on the path-->" + chromePathUnix);

                file = new File(chromePathUnix);
            }
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            Log.info("Property File Loaded");
            // get the property value and print it out
            value = prop.getProperty(property);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }
}