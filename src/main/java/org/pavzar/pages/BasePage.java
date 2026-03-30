package org.pavzar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    private final WebDriverWait wait;

    private static final String DECLINE_COOKIES_BUTTON_ID = "CybotCookiebotDialogBodyButtonDecline";

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void visit(String url) {
        driver.get(url);
    }

    protected WebElement waitVisibilityOfElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitPresenceOfElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> waitPresenceOfElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void clickByLocator(By locator) {
        WebElement element = waitVisibilityOfElement(locator);
        element.click();
    }

    protected void denyCookies() {
        clickByLocator(By.id(DECLINE_COOKIES_BUTTON_ID));
    }
}