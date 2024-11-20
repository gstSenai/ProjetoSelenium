package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TesteAmazon {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        int cont = 0;

        try {
            while (cont < 5) {
                try {
                    driver.get("https://www.amazon.com/");
                    driver.manage().window().maximize();

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-accountList")));
                    signInButton.click();

                    WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_email")));
                    emailInput.sendKeys("gustavo_souza12@estudante.sesisenai.org.br");

                    WebElement continueButton = driver.findElement(By.id("continue"));
                    continueButton.click();

                    WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
                    passwordInput.sendKeys("testes");

                    WebElement loginSubmitButton = driver.findElement(By.id("signInSubmit"));
                    loginSubmitButton.click();

                    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
                    searchBox.sendKeys("XBOX One");

                    WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
                    searchButton.click();

                    WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".s-main-slot .s-result-item .a-link-normal")));

                    WebElement productPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.a-price-whole")));

                    String priceText = productPriceElement.getText();
                    priceText = priceText.replace("$", "").replace(",", "").trim(); // Remove "$" e ","

                    double price = 0.0;
                    try {
                        price = Double.parseDouble(priceText);
                    } catch (NumberFormatException e) {
                        System.out.println("Não foi possível extrair o preço corretamente: " + priceText);
                        continue;
                    }

                    if (price < 100) {
                        firstProduct.click();
                        System.out.println("Produto clicado. Preço: $" + price);
                    } else {
                        System.out.println("Produto não clicado. Preço: $" + price);
                    }

                    WebElement accountMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-accountList")));
                    Actions actions = new Actions(driver);
                    actions.moveToElement(accountMenu).perform();

                    WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sign Out']")));
                    logoutButton.click();

                    WebElement signInAgain = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-accountList")));
                    System.out.println("Logout realizado com sucesso!");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                cont++;
            }

        } finally {
            driver.quit();
        }
    }
}