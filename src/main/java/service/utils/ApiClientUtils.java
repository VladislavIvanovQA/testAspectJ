package service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

@Slf4j
public class ApiClientUtils {
    public static final Function<Response, Response> handler = Response::peek;
//    public static ProjectConfig config;
    public static int WAIT_TIME;
//    public static Settings jsonSettings;
//    public static Profile profile;
    public static int MAX_TRY;
    public static boolean LOG;
    public static String CARD_NUMBER;
    public static String OTP_CODE;
    public static String FOI;
    public static String FOI_EDIT;
    private static String TOKEN;
    private static String BASE_URL;
    private static boolean PROXY;
    private static String PROXY_HOST;
    private static int PROXY_PORT;

    private static Supplier<RequestSpecBuilder> supplier() {
        return () -> base()
                .setContentType("Content-Type=application/json; charset=UTF-8");
    }

    public static RequestSpecBuilder base() {
//        config =
//                ConfigFactory.create(ProjectConfig.class,
//                        System.getProperties(),
//                        System.getenv());
//        BASE_URL = config.host();
//        OTP_CODE = config.otpCode();
//        WAIT_TIME = config.waitTime();
//        MAX_TRY = config.maxTry();
//        LOG = config.log();
//        PROXY = config.proxy();
//        PROXY_HOST = config.hostProxy();
//        PROXY_PORT = config.portProxy();
//        CARD_NUMBER = config.cardNumber();


        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Authorization", getToken())
//                .setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(gson())))
                .setConfig(newConfig().decoderConfig(decoderConfig().noContentDecoders()).
                        encoderConfig(EncoderConfig.encoderConfig()
                                .defaultContentCharset("UTF-8")
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setBaseUri(BASE_URL);
        if (PROXY) builder.setProxy(PROXY_HOST, PROXY_PORT);
        if (LOG) {
            builder.addFilter(new AllureRestAssured());
            builder.addFilter(new RequestLoggingFilter());
            builder.addFilter(new ResponseLoggingFilter());
        }
        return builder;
    }

//    public static ApiClient initClient() {
//        return ApiClient
//                .api(apiConfig()
//                        .reqSpecSupplier(supplier()));
//    }

    private static String getToken() {
        return TOKEN == null ? "" : TOKEN;
    }

    public static void setToken(String token) {
        ApiClientUtils.TOKEN = "Bearer " + token;
        log.info("Сохранение токена : " + ApiClientUtils.TOKEN);
    }

    public static String executeUUIDFromString(String string) {
        Pattern pairRegex = Pattern.compile("[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}");
        Matcher matcher = pairRegex.matcher(string);
        String uuid = "";
        while (matcher.find()) {
            uuid = matcher.group(0);
        }
        return uuid;
    }

    public static <T> T getRandomItem(List<T> T) {
        return T.get(ThreadLocalRandom.current().nextInt(0, T.size()));
    }

    public static RequestSpecification getSpecification(Object obj) {
        ObjectMapper map = new ObjectMapper();
        Map maps = map.convertValue(obj, Map.class);
        RequestSpecification spec = base().build();
        for (int i = 0; i < maps.size(); i++) {
            Object[] key = maps.keySet().toArray();
            Object[] value = maps.values().toArray();
            spec = spec.multiPart(getMultipart(key[i].toString(), value[i]));
        }
        return spec;
    }

    public static RequestSpecification getSpecificationFormParms(Object obj) {
        ObjectMapper map = new ObjectMapper();
        Map maps = map.convertValue(obj, Map.class);
        RequestSpecification spec = base().build();
        for (int i = 0; i < maps.size(); i++) {
            Object[] key = maps.keySet().toArray();
            Object[] value = maps.values().toArray();
            spec = spec.formParam(key[i].toString(), value[i]);
        }
        return spec;
    }

    public static MultiPartSpecification getMultipart(String fileName, Object content) {
        String contentType;
        if (fileName.equals("products") || fileName.equals("settings")) {
            contentType = "application/json";
        } else {
            if (fileName.equals("filePsa") ||
                    fileName.equals("filePts") ||
                    fileName.equals("filePassport") ||
                    fileName.equals("fileDeal")) {
                contentType = "image/png";
            } else {
                contentType = "text/plain";
            }
        }
        if (content == null) {
            return new MultiPartSpecBuilder("")
                    .charset(Charset.forName("UTF-8"))
                    .controlName(fileName)
                    .emptyFileName()
                    .mimeType(contentType)
                    .build();
        } else {
            return new MultiPartSpecBuilder(content)
                    .charset(Charset.forName("UTF-8"))
                    .controlName(fileName)
                    .emptyFileName()
                    .mimeType(contentType)
                    .build();
        }
    }
}
