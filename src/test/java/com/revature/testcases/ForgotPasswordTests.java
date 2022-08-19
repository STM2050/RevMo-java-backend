package com.revature.testcases;
import com.revature.webPages.ForgotPassword;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ForgotPasswordTests {
    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void validForgotPassword(){
        driver.get("http://127.0.0.1:5501/forgotpassword.html");
        ForgotPassword forgotPassword = new ForgotPassword(driver);

        forgotPassword.getEmailInput().sendKeys("vv@a.ca");

        forgotPassword.getSubmitButton().click();

        WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(30));

//        wdw.until(ExpectedCondition.visibilityofElementLocated(By.id("Error Message")))

    }
}
