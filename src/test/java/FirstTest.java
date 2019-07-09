import condition.Conditions;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static condition.Conditions.bodyField;
import static org.hamcrest.Matchers.*;

public class FirstTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        authAndInit("79000000013");
    }

    @Step("Авторизация за {phone}")
    private void authAndInit(String s) {
    }

    @Test
    public void getRegistries() {
        adminPointsApiService.getPoints()
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(bodyField("all.length", not(is(0))));
    }
}
