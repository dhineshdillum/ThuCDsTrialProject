package steps;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.steps.ScenarioSteps;
import org.jbehave.core.annotations.AfterScenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dhinesh.dillum on 09/12/16.
 */
public class EndUserSteps extends ScenarioSteps {
    @Managed
    WebDriver driver;

    public void openWebPage(String siteName) {
        if (siteName.equalsIgnoreCase("Yahoo")) {
            getDriver().get("http:www.yahoo.com");
        }
        else if (siteName.equalsIgnoreCase("Javascript")) {
            getDriver().get("http://www.anaesthetist.com/mnm/javascript/calc.htm");
        }
        else if (siteName.equalsIgnoreCase("Salesforce")) {
            getDriver().get("https://cs81.salesforce.com/console");
        }
        else if (siteName.equalsIgnoreCase("Etsy")) {
            getDriver().get("https://www.etsy.com/uk/listing/465419784/printed-silk-kimono-top-blur");
        }

    }

    public void getSiteTitle(String expectedSiteTitle) {
        String actualSiteTitleIs = getDriver().getTitle();
 //       assertThat(actualSiteTitleIs.equals(expectedSiteTitle), is(true));
        System.out.println("actualSiteTitleIs = " + actualSiteTitleIs);
    }



    public void getSiteTitleUsingJavaScriptExecutor() {
        //String pandu = (String) ((JavascriptExecutor) getDriver()).executeScript("return document.title");
        JavascriptExecutor js =(JavascriptExecutor)getDriver();
        String pandu = (String) js.executeScript("return document.title");
        System.out.println("site title = " + pandu);
    }

    public void T04_refreshBrowser() {
        displayContextDialog();
        JavascriptExecutor js =(JavascriptExecutor)getDriver();
        waitFor(6000);
        js.executeScript("history.go(0);");
        System.out.println(" Did it refresh the page? ");
    }

    public void T02_alertPopUpWindow(){
        JavascriptExecutor js =(JavascriptExecutor)getDriver();
        js.executeScript("alert('SW Test Academy!');");
        getDriver().switchTo().alert().accept();
    }

    public void T06_getURL(){
        JavascriptExecutor js =(JavascriptExecutor)getDriver();
        String currentUrl= (String) js.executeScript("return document.URL;");
        assertThat(currentUrl, is(getDriver().getCurrentUrl()));
    }

    public void T08_hideAndShowElement() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementsByName('five')[0].style.display='none'"); //Hide an element
        waitFor(3000);
        js.executeScript("document.getElementsByName('five')[0].style.display='block'");//Show an element
        waitFor(3000);
    }

    public void displayContextDialog() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.confirm('do you agree')"); //Hide an element
        waitFor(3000);
        getDriver().switchTo().alert().accept();
    }

    public void waitFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Close Driver
    @AfterScenario
    public void quitDriver() {
        getDriver().quit();
    }


    public void userSupplyValidLoginCredentials() {
        SalesForceLoginPage LoginPage = getPages().get(SalesForceLoginPage.class);
        LoginPage.enterAdminCredientials();
        LoginPage.clickOnLogin();
    }

    public void UserSelectOption() {
        SalesForceHomePage homePage = getPages().get(SalesForceHomePage.class);
        homePage.selectOptionInDropDownList();
    }


    public void getSrc() {
        SalesForceLoginPage LoginPage = getPages().get(SalesForceLoginPage.class);
        LoginPage.getSrcforImages();

    }

    public static OAActor t;
    public <T extends Actor> void performsAs(T t) {
        OAActor actor = (OAActor) t;
}

    public void getResetPasswordLink() {
        GoogleMail googleMail = getPages().get(GoogleMail.class);
        googleMail.getResetPasswordLink(t);
    }

    public void createAndSendEmail() throws MessagingException, IOException {
        GoogleMail googleMail = getPages().get(GoogleMail.class);

        googleMail.createEmail("xxx@yyy.co.uk", "xxx1@yyy.com",
                "Sending Email with BodyText", "This email has a text message");
        googleMail.sendMessage();
    }

    public void createEmailWithAttachmentAndSend(String path) throws MessagingException, IOException {
        File file = new File(path);
        GoogleMail googleMail = getPages().get(GoogleMail.class);

        googleMail.createEmailWithAttachment("xxx@yyy.co.uk", "xxx1@yyy.com",
                "Sending Email with an Attachment", "This email has an attachment", file);
        googleMail.sendMessage(); //The special value "me" is used to indicate the user is already authenticated
    }

    public void createADraft() throws MessagingException, IOException {
        GoogleMail googleMail = getPages().get(GoogleMail.class);

        googleMail.createEmail("xxx@yyy.co.uk.co.uk", "xxx1@yyy.com",
                "Draft Email", "This is a draft email");
        googleMail.createDraftMessage();
    }
}
