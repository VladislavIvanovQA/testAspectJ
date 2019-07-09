package service;

import assertions.AssertableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class AdminPointsApiService {
//    private final AdminPointsApi adminPointsApiService;

    public AdminPointsApiService() {
//        adminPointsApiService = initClient().adminPoints();
    }

    public enum Keys {
        limit,
        setting,
        tariff
    }

    //@Step("Получение списка ПЗУ")
    public AssertableResponse getPoints() {
        log.info("Получение списка ПЗУ");
        Response then = given()
                .with()
                .queryParam("animal_type", "cat")
                .get("https://cat-fact.herokuapp.com/facts")
                .then()
                .extract()
                .response();
        return new AssertableResponse(then);
    }
}
