package ua.laposhko.hmt.config;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author Sergey Laposhko
 */
public class Log4JConfig {

    public static void configLog4J() {
        try {
            PropertyConfigurator.configure("log4j.properties");
        } catch (Exception ex) {
        }
    }

}
