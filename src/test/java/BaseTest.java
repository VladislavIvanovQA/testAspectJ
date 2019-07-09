import io.qameta.allure.Step;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import service.AdminPointsApiService;

public class BaseTest {
    protected AdminPointsApiService adminPointsApiService;

    @BeforeMethod
    @Step("Инициализация сервисов")
    public void init() {
        initService();
    }

    private void initService() {
        adminPointsApiService = new AdminPointsApiService();
    }

    @AfterClass
    @Step("Завершение")
    public void tearDown() {
        logout();
    }

    @Step("Log out")
    private void logout() {

    }
}
