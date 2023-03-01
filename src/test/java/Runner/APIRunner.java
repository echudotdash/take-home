package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(

        features = "src/test/resources/Features",
        glue = "APISteps",
        dryRun = false,
        tags = "@API1",
        monochrome = true,
        strict = true,

        plugin = {"pretty", "json:target/cucumber.json","html:target/cucumber.html",
                "rerun:target/failed.txt"
        }


)

public class APIRunner {
}
