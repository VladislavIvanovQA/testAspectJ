package condition;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;

@UtilityClass
@Slf4j
public class Conditions {

    //@Step("Проверка кода ответа {statusCode}")
    public static StatusCodeCondition statusCode(int statusCode) {
        log.info("Ответ содержит код ответа {}", statusCode);
        return new StatusCodeCondition(statusCode);
    }

    //@Step("Проверка значения в теле ответа {jsonPath} на соотвествие {matcher}")
    public static BodyJsonCondition bodyField(String jsonPath, Matcher matcher) {
        log.info("Значение в теле ответа {} соотвествует ожидаемому {}", jsonPath, matcher);
        return new BodyJsonCondition(jsonPath, matcher);
    }

    //@Step("Проверка на соотвествие {matcher}")
    public static BodyHtmlCondition bodyField(Matcher matcher) {
        log.info("Ответ содержит {}", matcher);
        return new BodyHtmlCondition(matcher);
    }

    //@Step("Проверка значения в заголовке ответа {headerValue} на соотвествие {matcher}")
    public static HeaderCondition headerField(String headerValue, Matcher matcher){
        log.info("Значение {} в заголовке ответа ожидается {}", headerValue, matcher);
        return new HeaderCondition(headerValue, matcher);
    }
}
