package steps;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dhinesh.dillum on 03/02/17.
 */
public class SalesForceLoginPage  extends PageObject {

    private String adminUsername;
    private String adminPassword;

    private WebElementFacade username() 	 { return element(By.xpath("//*[@name='username']"));		}
    private WebElementFacade password() 	 { return element(By.xpath("//*[@id='password']"));			}
    private WebElementFacade loginbutton()   { return element(By.xpath("//*[@name='Login']"));}

    public void configuration() {
        BufferedReader chpConfig=null;
        try {
            chpConfig = new BufferedReader(new FileReader("testConfig"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            Properties prop = new Properties();
            prop.load(chpConfig);
            adminUsername = prop.getProperty("UserId");
            adminPassword = prop.getProperty("UserPasswd");
        } catch (Exception configFN) {
            configFN.printStackTrace();
        }
    }

    public void enterAdminCredientials() {
        waitFor(3).seconds();
        configuration();
        getDriver().manage().window().maximize();
        username().type(adminUsername);
        waitABit(1);
        password().type(adminPassword);

    }

    public void clickOnLogin() {
        waitABit(1);
        loginbutton().click();
        waitFor(5).seconds();
        try {
            getDriver().switchTo().alert().dismiss();
        } catch (Exception ee) {
        }

        waitFor(3).seconds();
    }

    public void getSrcforImages() {
        waitFor(3).seconds();

        WebElement dropDown = getDriver().findElement( By.xpath( "//*[@id='circles']" ) );
        List<WebElement> drop = dropDown.findElements(By.cssSelector(".thumbnail-nav img"));
        System.out.println(" Size" + drop.size());

        //to get src of 1 element
        String s = drop.get(0).getAttribute("src");
        System.out.println("string s is " +s);

        for (WebElement we: drop) {
            String src = we.getAttribute("src");
            System.out.println("src of all elements " + src);
        }

    }
}
