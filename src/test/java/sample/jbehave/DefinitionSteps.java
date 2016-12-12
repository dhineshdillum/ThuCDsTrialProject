package sample.jbehave;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.*;
import org.junit.After;
import steps.EndUserSteps;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

/**
 * Created by dhinesh.dillum on 08/12/16.
 */
public class DefinitionSteps {

    @Steps
    EndUserSteps endUserStep;

    @Given("User is on the [site]")
    public void givenUserIsOnThesite(String site) {
        endUserStep.openWebPage(site);

    }

    @When("the site Title is [title]")
    public void thenTheSiteTitleIs(String title) {
        endUserStep.getSiteTitle(title);
    }

//    @AfterScenario
//    public void afterSE() {
//        getDriver().close();
//    }

    @After
    public void afterSE() {
        getDriver().close();
    }
}
