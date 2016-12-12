package jbehave;

import org.jbehave.core.annotations.BeforeScenario;

/**
 * Created by dhinesh.dillum on 09/12/16.
 */
public class WebDriverConfigurer {

        public static String getOS() {
            String OSName = System.getProperty("os.name");
            System.out.println("print os" +OSName);
            return OSName;
        }

        //@BeforeScenario
        public static void setDriverPath() {

            if (getOS().contains("Mac")) {
                System.setProperty("webdriver.chrome.driver", "/chromedriver 2");
            }
            else if (getOS().contains("win")) {
                System.setProperty("webdriver.chrome.driver", "\\windows\\chromedriver2.exe");
            }
        }

    }
