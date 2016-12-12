package sample.jbehave;


import jbehave.WebDriverConfigurer;
import net.thucydides.jbehave.ThucydidesJUnitStories;

/**
 * Created by dhinesh.dillum on 08/12/16.
 */
public class taskRunner extends ThucydidesJUnitStories {

    private static final String STORY_NAME_PATTERN = "**/${jbehave.story.name:*}.story";

    public taskRunner() {
        WebDriverConfigurer.setDriverPath();

        findStoriesCalled(storyNamesFromEnvironmentVariable());

        findStoriesCalled("TrialTest.story");
    }

    @Override
    public void run() throws Throwable {
        super.run();
    }

    private String storyNamesFromEnvironmentVariable() {
        return SystemPropertyUtils.resolvePlaceholders(STORY_NAME_PATTERN);
    }

}
