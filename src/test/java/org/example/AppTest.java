package org.example;
import com.codeborne.selenide.*;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public WebDriver driver;
    /**
     * Rigorous Test :-)
     */
    @Before
    public void before() {

        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        WebDriverRunner.setWebDriver(driver);
        Configuration.timeout = 3000;

    }

    @After
    public void After() {
        System.out.println("Тест завершен");
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void testLogin() {
        open("https://dev.n7lanit.ru/");
        /*Тест авторизации*/
        $(By.xpath("//button[contains(text(), 'Войти')]/..")).should(Condition.visible).click();
        $(By.xpath("//*[@id='id_username']")).should(Condition.visible).sendKeys("TestCor");
        $(By.xpath("//*[@id='id_password']")).should(Condition.visible).sendKeys("1111111g");
        $(By.xpath("//button[@class='btn btn-primary btn-block']")).should(Condition.visible).click();
        $(By.xpath("//*[@id=\"user-menu-mount\"]/ul/li[3]/a/img")).shouldBe(Condition.visible);
    }

    @Test
    public void testRandomTheme() {

        /*Оставляем тестовый комментарий*/
        $(By.xpath("//button/span[contains(text(), 'Категория')]/..")).should(Condition.visible).click();
        $(By.xpath("//a[@class='btn btn-link']")).should(Condition.visible).click();

        ElementsCollection collection = $$(By.xpath("//span[@class='thread-detail-replies' and not(preceding-sibling::span)]/ancestor::div[3]/a"));
        collection.get((int) (collection.size()*Math.random())).click();
        $(By.xpath("//div[@class='col-sm-4 hidden-xs']/button[@class='btn btn-primary btn-block btn-outline']")).shouldHave(text("Ответить")).click();
        $(By.xpath("//textarea[@id='editor-textarea']")).shouldBe(Condition.visible).val("Интересный текст для сообщения").submit();
        $(By.xpath("//div[@class='post-body']/article/p[contains(text(),'Интересный текст для сообщения')]")).isDisplayed();
        $(By.xpath("//ul[@class='nav navbar-nav']//a[contains(text(),'Темы')]")).shouldBe(Condition.visible).click();

    }

    @Test
    public void testThemeRepeat() {
        /*Повторяем тест, но с другим комминтарием*/
        ElementsCollection collection = $$(By.xpath("//span[@class='thread-detail-replies' and not(preceding-sibling::span)]/ancestor::div[3]/a"));
        collection.get((int) (collection.size()*Math.random())).shouldBe(Condition.visible).click();
        $(By.xpath("//div[@class='col-sm-4 hidden-xs']/button[@class='btn btn-primary btn-block btn-outline']")).shouldHave(text("Ответить")).click();
        Selenide.sleep(6000);
        $(By.xpath("//textarea[@id='editor-textarea']")).shouldBe(Condition.visible).val("Другой интересный текст для сообщения").submit();
        $(By.xpath("//div[@class='post-body']/article/p[contains(text(),'Другой интересный текст для сообщения')]")).isDisplayed();
        $(By.xpath("//ul[@class='nav navbar-nav']//a[contains(text(),'Темы')]")).shouldBe(Condition.visible).click();
    }
}
