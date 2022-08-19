package com.revature.webPages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPassword {
    private WebDriver driver;

    public ForgotPassword(WebDriver driver){
        this.driver = driver;

    }
    public WebElement getEmailInput(){
        return driver.findElement(By.id("email"));
    }
    public WebElement getSubmitButton(){
        return driver.findElement(By.id("email_submit"));
    }
    public WebElement getSignInLink(){
        return driver.findElement(By.linkText("Go to Sign In"));
    }
}
