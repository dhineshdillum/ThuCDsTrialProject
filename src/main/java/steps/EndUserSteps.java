package steps;

import org.jbehave.core.annotations.AfterScenario;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by dhinesh.dillum on 09/12/16.
 */
public class EndUserSteps {

    public void openWebPage(String siteName) {
        if (siteName.equalsIgnoreCase("BBC")) {
            getDriver().get("http://www.bbc.com");
        }
    }

    public void getSiteTitle(String expectedSiteTitle) {
        String actualSiteTitleIs = getDriver().getTitle();
        assertThat(actualSiteTitleIs.equals(expectedSiteTitle), is(true));
    }



}
