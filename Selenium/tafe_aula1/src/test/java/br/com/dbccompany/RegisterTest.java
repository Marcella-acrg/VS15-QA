package br.com.dbccompany;

import br.com.dbccompany.data.factory.BaseDataFactory;
import br.com.dbccompany.data.factory.UsuarioDataFactory;
import br.com.dbccompany.model.UsuarioModel;
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
    public void abrirNavegador(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.get("https://www.automationexercise.com");
        driver.manage().window().maximize();
    }

    @Test
    public void deveFazerCadastroComSucesso(){
        UsuarioModel usuario = UsuarioDataFactory.usuarioValido();

        String btnLogin = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a";
        String btnSignup = "#form > div > div > div:nth-child(3) > div > form > button";
        String btnCreateAccount = "#form > div > div > div > div > form > button";
        String btnContinue = "#form > div > div > div > div > a";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[style=\"color: orange;\"]")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnLogin)));
        driver.findElement(By.cssSelector(btnLogin)).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.automationexercise.com/login");
        String signupMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".signup-form > h2"))).getText();
        Assert.assertEquals(signupMessage, "New User Signup!");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"signup-name\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"signup-name\"]")).sendKeys(usuario.getFullName());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"signup-email\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"signup-email\"]")).sendKeys(usuario.getEmail());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnSignup)));
        driver.findElement(By.cssSelector(btnSignup)).click();

        Assert.assertEquals(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div > h2:nth-child(1) > b"))).getText(), "ENTER ACCOUNT INFORMATION");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(UsuarioDataFactory.novoUsuario().getGender())));
        driver.findElement(By.cssSelector(usuario.getGender())).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"password\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"password\"]")).sendKeys(usuario.getPassword());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[data-qa=\"days\"]")));
        driver.findElement(By.cssSelector("select[data-qa=\"days\"]")).sendKeys(usuario.getBirthDay());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[data-qa=\"months\"]")));
        driver.findElement(By.cssSelector("select[data-qa=\"months\"]")).sendKeys(usuario.getBirthMonth());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[data-qa=\"years\"]")));
        driver.findElement(By.cssSelector("select[data-qa=\"years\"]")).sendKeys(usuario.getBirthYear());
        driver.findElement(By.cssSelector("input[id=\"newsletter\"]")).click();
        driver.findElement(By.cssSelector("input[id=\"optin\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"first_name\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"first_name\"]")).sendKeys(usuario.getLastname());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"last_name\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"last_name\"]")).sendKeys(usuario.getLastname());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"company\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"company\"]")).sendKeys(usuario.getCompany());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"address\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"address\"]")).sendKeys(usuario.getAddress());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"address2\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"address2\"]")).sendKeys(usuario.getAddress2());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[data-qa=\"country\"]")));
        driver.findElement(By.cssSelector("select[data-qa=\"country\"]")).sendKeys(usuario.getCountry());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"state\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"state\"]")).sendKeys(usuario.getState());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"city\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"city\"]")).sendKeys(usuario.getCity());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"zipcode\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"zipcode\"]")).sendKeys(usuario.getZipcode());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-qa=\"mobile_number\"]")));
        driver.findElement(By.cssSelector("input[data-qa=\"mobile_number\"]")).sendKeys(usuario.getMobileNumber());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnCreateAccount)));
        driver.findElement(By.cssSelector(btnCreateAccount)).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.automationexercise.com/account_created");
        String successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2[data-qa=\"account-created\"]"))).getText();
        Assert.assertEquals(successMessage, "ACCOUNT CREATED!");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnContinue)));
        driver.findElement(By.cssSelector(btnContinue)).click();
        String loggedInText = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li:nth-child(10)"))).getText();
        Assert.assertEquals(loggedInText, "Logged in as " + usuario.getFullName());

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href=\"/delete_account\"]"))).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.automationexercise.com/delete_account");
        String deletedMessage =  wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2[data-qa=\"account-deleted\"]"))).getText();
        Assert.assertEquals(deletedMessage, "ACCOUNT DELETED!");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[data-qa=\"continue-button\"]"))).click();
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