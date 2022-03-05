package Utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {
    public static String setEnvValue(String key, String value) throws ConfigurationException {
        PropertiesConfiguration props = new PropertiesConfiguration("./src/test/resources/config.properties");
        props.setProperty(key, value);
        props.save();
        return value;


    }

}
