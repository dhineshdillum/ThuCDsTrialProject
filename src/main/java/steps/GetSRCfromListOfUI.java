package steps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

/**
 * Created by dhinesh.dillum on 16/02/17.
 */
public class GetSRCfromListOfUI {

    //FirefoxDriver driver;
    @Before
    public void startUp(){
        System.out.println("Started");
        //System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        System.setProperty("webdriver.chrome.driver", "/chromedriver 2");
        getDriver().get("http:www.yahoo.com");
        System.out.println(" ********** Setting Property - Done *********");

        //driver = new FirefoxDriver();
        //driver.get("https://www.etsy.com/uk/listing/465419784/printed-silk-kimono-top-blur");
    }

    @Test
    public void myGetSRC(){
        System.out.println("Test begins");
        System.out.println(getDriver().getTitle());

//Need to get total count of il from ul
    //   ** once we get the count, go to each il and get src from each il


    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(4000);

        System.out.println("Tearing down.");
        getDriver().quit();
    }
}
