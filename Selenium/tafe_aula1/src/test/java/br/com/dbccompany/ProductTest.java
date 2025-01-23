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

public class ProductTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

    String btnProducts = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(2) > a";
    String listProducts = "div.col-sm-9.padding-right > div > div:nth-child(3)";
    String btnFirstProduct = "div:nth-child(3) > div > div.choose > ul > li > a";
    String btnAddToCart = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > span > button";
    String btnViewCart = "[id=\"cartModal\"] a[href=\"/view_cart\"]";

    @BeforeTest
    public void abrirNavegador() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.get("https://www.automationexercise.com");
        driver.manage().window().maximize();
    }

    @Test
    public void deveExibirProdutosComSucesso(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnProducts)));
        driver.findElement(By.cssSelector(btnProducts)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"title text-center\"]")));
        String txtAllProducts = driver.findElement(By.cssSelector("[class=\"title text-center\"]")).getText();
        Assert.assertEquals(txtAllProducts, "ALL PRODUCTS");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(listProducts)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnFirstProduct)));
        driver.findElement(By.cssSelector(btnFirstProduct)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"product-information\"] h2")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"product-information\"] h2")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-information p:nth-of-type(1)")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-information span span")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-information p:nth-of-type(2)")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-information p:nth-of-type(3)")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-information p:nth-of-type(4)")));
    }

    @Test
    public void deveVerificarQuantidadeDeItensNoCarrinho() throws InterruptedException {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[style=\"color: orange;\"]")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnProducts))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(listProducts)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnFirstProduct)));
        driver.findElement(By.cssSelector(btnFirstProduct)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#quantity"))).clear();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#quantity"))).sendKeys("4");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnAddToCart))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(btnViewCart)));
        driver.findElement(By.cssSelector(btnViewCart)).click();

        String quantidade = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cart_quantity > button"))).getText();
        Assert.assertEquals(quantidade, "4");
    }

    @AfterTest
    public void finalizarNavegador(){
        driver.quit();
    }
}
