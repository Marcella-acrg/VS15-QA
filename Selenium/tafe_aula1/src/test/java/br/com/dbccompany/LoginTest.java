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

public class LoginTest {

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
    public void deveFazerLoginComSucesso(){

        String btnSignupLogin = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a";
        String btnLogin = "#form  div div div.col-sm-4.col-sm-offset-1 div  form > button";
        String lblLogged = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10) > a";
        String btnDelete = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(5) > a";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnSignupLogin)));
        driver.findElement(By.cssSelector(btnSignupLogin)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"login-email\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"login-email\"]")).sendKeys(BaseDataFactory.emailValidoLogin());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"login-password\"]")));
        driver.findElement(By.cssSelector("[data-qa=\"login-password\"]")).sendKeys(BaseDataFactory.senhaValida());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnLogin)));
        driver.findElement(By.cssSelector(btnLogin)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(lblLogged)));
        String textLblLogged = driver.findElement(By.cssSelector(lblLogged)).getText();
        Assert.assertEquals(textLblLogged, "Logged in as Usuário Teste");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnDelete)));
        driver.findElement(By.cssSelector(btnDelete)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"account-deleted\"] b")));
        String textAccDeleted = driver.findElement(By.cssSelector("[data-qa=\"account-deleted\"] b")).getText();
        Assert.assertEquals(textAccDeleted, "ACCOUNT DELETED!");

    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }
}