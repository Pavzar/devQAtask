package org.pavzar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {

    private static final String PLAYTECHPEOPLE_HOME_PAGE_URL = "https://www.playtechpeople.com";
    private static final String PLAYTECH_TEAMS_CSS = "#component-4 .teams-cards .team-card h6 > span";
    private static final String LIFE_AT_PLAYTECH_TAB_ID = "menu-item-49";
    private static final String WHO_WE_ARE_ID = "menu-item-47";
    private static final String ABOUT_US_TEXT_CSS = "p.banner-uppertitle";
    private static final String HOME_BANNER_ID = "component-1";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTeams() {
        return waitPresenceOfElements(By.cssSelector(PLAYTECH_TEAMS_CSS)).stream()
                .map(WebElement::getText)
                .toList();
    }

    public void visitHomePage() {
        visit(PLAYTECHPEOPLE_HOME_PAGE_URL);
        denyCookies();
        waitVisibilityOfElement(By.id(HOME_BANNER_ID));
    }

    public LifeAtPlaytechPage visitLifeAtPlaytechPage() {
        clickByLocator(By.id(LIFE_AT_PLAYTECH_TAB_ID));
        clickByLocator(By.id(WHO_WE_ARE_ID));
        waitVisibilityOfElement(By.cssSelector(ABOUT_US_TEXT_CSS));

        return new LifeAtPlaytechPage(driver);
    }
}
