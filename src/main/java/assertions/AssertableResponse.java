package assertions;

import condition.Condition;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AssertableResponse {
    private final Response response;

    public AssertableResponse shouldHave(Condition conditions) {
        conditions.check(response);
        return this;
    }

    public <T> T getJsonPathValue(String jsonPath) {
        log.info("Извлчение значения из тела ответа по JsonPath {}", jsonPath);
        return response
                .then()
                .extract()
                .jsonPath()
                .get(jsonPath);
    }

    public String getHtmlPathValue(String htmlPath) {
        log.info("Извлчение значения из тела ответа по HtmlPath {}", htmlPath);
        return response
                .then()
                .extract()
                .htmlPath()
                .getString(htmlPath).replace("\"", "");
    }

    public String getHeaderValue(String headerValue) {
        log.info("Извлчение значения из заголовка ответа {}", headerValue);
        return response
                .then()
                .extract()
                .header(headerValue);
    }

    public <T> T asPOJO(Class<T> tClass) {
        return response
                .then()
                .extract()
                .as(tClass);
    }

    public ExtractableResponse<Response> execute() {
        return response
                .then()
                .extract();
    }

    public AssertableResponse logAll() {
        response.then().log().all();
        return this;
    }
}
