package condition;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

@RequiredArgsConstructor
public class BodyHtmlCondition implements Condition {
    private final Matcher htmlPath;


    @Override
    public void check(Response response) {
        response.then()
                .body(htmlPath);
    }

    @Override
    public String toString() {
        return "BodyHtmlCondition{" +
                "htmlPath=" + htmlPath +
                '}';
    }
}
