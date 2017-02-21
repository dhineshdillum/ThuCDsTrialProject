package sample.jbehave;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.*;
import steps.EndUserSteps;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by dhinesh.dillum on 08/12/16.
 */
public class DefinitionSteps {

    @Steps
    EndUserSteps enduserstep;

    @Given("User is on the [site]")
    public void givenUserIsOnThesite(String site) {
        enduserstep.openWebPage(site);

    }

    @When("the site Title is [title]")
    public void thenTheSiteTitleIs(String title) {
        enduserstep.getSiteTitle(title);
    }

//    @AfterScenario
//    public void afterSE() {
//        getDriver().close();
//    }

    @Then("User should be able to get page title")
    public void thenUserShouldBeAbleToGetPagetitle() {
        enduserstep.getSiteTitleUsingJavaScriptExecutor();
    }

    @When("User refreshes the page")
    public void andUserRefreshesThePage() {
        enduserstep.T04_refreshBrowser();
    }

    @When("User logins to the site")
    public void whenUserLoginsToTheSite() {
        enduserstep.userSupplyValidLoginCredentials();
    }

    @When("User selects Customer option in drop down list")
    public void whenUserSelectsCustomerOptionInDropDownList() {
        enduserstep.UserSelectOption();
    }

    @When("User gets src for all mini images")
    public void whenUserGetsSrcForAllMiniImages() {
        enduserstep.getSrc();
    }

    @Given("user wants to get resetPasswordLink from an email")
    public void givenUserWantsToGetResetPasswordLinkFromAnEmail() {
        enduserstep.getResetPasswordLink();
    }

    @Given("user creates an email which has plain text and sends it")
    public void givenUserCreatesAnEmailWhichHasPlainTextAndSendsIt() throws IOException, MessagingException {
        enduserstep.createAndSendEmail();
    }

    @Given("user creates an email and add following $attachment and sends it")
    public void givenUserCreatesAnEmailAndAddFollowingattachmentAndSendsIt(String attachment) throws MessagingException, IOException {
        enduserstep.createEmailWithAttachmentAndSend(attachment);
    }

    @Given("user creates a draft message email with plain text")
    public void givenUserCreatesADraftMessageEmailWithPlainText() throws MessagingException, IOException {
        enduserstep.createADraft();
    }
}
