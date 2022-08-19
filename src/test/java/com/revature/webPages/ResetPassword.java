package com.revature.webPages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class ResetPassword {
    private WebDriver driver;

    public ResetPassword(WebDriver driver){
        this.driver = driver;

    }
    public WebElement getNewPasswordInput(){
        return driver.findElement(By.id("new_password"));
    }
    public WebElement getConfirmPasswordInput(){
        return driver.findElement(By.id("confirm_password"));
    }
    public WebElement getResetPassword(){
        return driver.findElement(By.id("reset_password"));
    }
}
