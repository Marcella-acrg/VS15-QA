package br.com.dbccompany;

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

public class VerifyCasesPageTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeTest
    public void abrirNavegador(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.get("https://www.automationexercise.com");
        driver.manage().window().maximize();
    }

    @Test
    public void deveVerificarAPaginaDeCasosDeTestes(){

        String btnCasosDeTeste = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(5) > a";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[style=\"color: orange;\"]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnCasosDeTeste)));
        driver.findElement(By.cssSelector(btnCasosDeTeste)).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.automationexercise.com/test_cases");
    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }

}
