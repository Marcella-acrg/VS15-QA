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

public class CartTest {
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
    public void deveInscreverEmCarrinhoComSucesso(){
        String btnCart = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(3) > a";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnCart)));
        driver.findElement(By.cssSelector(btnCart)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"single-widget\"] h2")));
        driver.findElement(By.cssSelector("[id=\"susbscribe_email\"]")).sendKeys(BaseDataFactory.emailAleatorio());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"btn btn-default\"]")));
        driver.findElement(By.cssSelector("[class=\"btn btn-default\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"alert-success alert\"]")));
        String txtSubscriptionOk = driver.findElement(By.cssSelector("[class=\"alert-success alert\"]")).getText();
        Assert.assertEquals(txtSubscriptionOk, "You have been successfully subscribed!");
    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }
}
