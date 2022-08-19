package com.revature.testcases;
import com.revature.webPages.ForgotPassword;
import com.revature.webPages.ResetPassword;
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

public class ResetPasswordTests {
    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
}
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void validForgotPassword(){
        driver.get("http://127.0.0.1:5501/resetpassword.html");
        ResetPassword resetPassword = new ResetPassword(driver);

        resetPassword.getNewPasswordInput().sendKeys("P@ssword7");

        resetPassword.getConfirmPasswordInput().sendKeys("P@ssword7");

        resetPassword.getResetPassword().click();

        WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(30));

//        wdw.until(ExpectedCondition.visibilityofElementLocated(By.id("Error Message")))

    }
}

