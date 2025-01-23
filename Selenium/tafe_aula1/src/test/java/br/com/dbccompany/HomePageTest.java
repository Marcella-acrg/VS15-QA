package br.com.dbccompany;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class VerifySubscriptionInHomePageTest {
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
    public void deveVerificarSubscricaoPaginaInicial(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[style=\"color: orange;\"]")));
        driver.get("https://www.automationexercise.com/#footer");
        WebElement subscriptionText = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".footer-widget h2")));
        Assert.assertEquals(subscriptionText.getText(), "SUBSCRIPTION");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id=\"susbscribe_email\"]"))).sendKeys("teste@teste.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[id=\"subscribe\"]"))).click();

        Assert.assertFalse( wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id=\"success-subscribe\"]"))).getAttribute("class").contains("hide"));
    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }
}
