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

    String btnSignupLogin = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a";
    String btnLogin = "#form  div div div.col-sm-4.col-sm-offset-1 div  form > button";
    String lblLogged = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10) > a";
    String btnDelete = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(5) > a";
    String btnLogout = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a";

    @BeforeTest
    public void abrirNavegador() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.get("https://www.automationexercise.com");
        driver.manage().window().maximize();
    }

    @Test
    public void deveFazerLoginComSucesso(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnSignupLogin)));
        driver.findElement(By.cssSelector(btnSignupLogin)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"login-email\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"login-email\"]")).sendKeys(BaseDataFactory.loginValido().getEmail());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"login-password\"]")));
        driver.findElement(By.cssSelector("[data-qa=\"login-password\"]")).sendKeys(BaseDataFactory.loginValido().getPassword());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnLogin)));
        driver.findElement(By.cssSelector(btnLogin)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(lblLogged)));
        String textLblLogged = driver.findElement(By.cssSelector(lblLogged)).getText();
        Assert.assertEquals(textLblLogged, "Logged in as ");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnDelete)));
        driver.findElement(By.cssSelector(btnDelete)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"account-deleted\"] b")));
        String textAccDeleted = driver.findElement(By.cssSelector("[data-qa=\"account-deleted\"] b")).getText();
        Assert.assertEquals(textAccDeleted, "ACCOUNT DELETED!");

    }

    @Test
    public void deveFazerLogoutComSucesso(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[style=\"color: orange;\"]")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnSignupLogin)));
        driver.findElement(By.cssSelector(btnSignupLogin)).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.automationexercise.com/login");
        String signupMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".login-form > h2"))).getText();
        Assert.assertEquals(signupMessage, "Login to your account");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"login-email\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"login-email\"]")).sendKeys(BaseDataFactory.loginValido().getEmail());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa=\"login-password\"]")));
        driver.findElement(By.cssSelector("[data-qa=\"login-password\"]")).sendKeys(BaseDataFactory.loginValido().getPassword());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnLogin)));
        driver.findElement(By.cssSelector(btnLogin)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(lblLogged)));
        String textLblLogged = driver.findElement(By.cssSelector(lblLogged)).getText();
        Assert.assertEquals(textLblLogged, "Logged in as ");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnLogout)));
        driver.findElement(By.cssSelector(btnLogout)).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.automationexercise.com/login");
        String loginMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".signup-form > h2"))).getText();
        Assert.assertEquals(loginMessage, "Login to your account");

    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }
}