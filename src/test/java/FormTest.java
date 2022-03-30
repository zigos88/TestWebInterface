import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
   public void testOfTrueValidation() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Алексей Иванов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79458352453");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(actualText, expected);
    }

    @Test
    public void testNotTrueValidationOfEnglishName() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Alexey Ivanov");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79458352453");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(actualText, expected);
    }

    @Test
    public void testNotTrueValidationOfEmptyName() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79458352453");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(actualText, expected);
    }

    @Test
    public void testNotTrueValidationOfIncorrectPhone() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Алексей");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("6454545454545545");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(actualText, expected);
    }

    @Test
    public void testIfNotClickCheckbox() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Алексей Иванов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79458352453");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'agreement'].input_invalid .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(actualText, expected);
    }










}
