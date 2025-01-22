package br.com.dbccompany;

import br.com.dbccompany.data.factory.BaseDataFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegisterTest {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeTest
    public void abrirNavegador() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.get("https://www.automationexercise.com");
        driver.manage().window().maximize();
    }

    @Test
    public void tentarRegistrarUsuarioComEmailEmUso(){
        String btnSignupLogin = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a";
        String lblEmailExist = "#form > div > div > div:nth-child(3) > div > form > p";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnSignupLogin)));
        driver.findElement(By.cssSelector(btnSignupLogin)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"signup-form\"] h2")));
        driver.findElement(By.cssSelector("[data-qa=\"signup-name\"")).sendKeys(BaseDataFactory.nomeValido());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"signup-email\"")));
        driver.findElement(By.cssSelector("[data-qa=\"signup-email\"")).sendKeys(BaseDataFactory.emailValido());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"signup-button\"")));
        driver.findElement(By.cssSelector("[data-qa=\"signup-button\"")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(lblEmailExist)));

        String txtEmailExist = driver.findElement(By.cssSelector(lblEmailExist)).getText();
        Assert.assertEquals(txtEmailExist, "Email Address already exist!");
    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }
}
