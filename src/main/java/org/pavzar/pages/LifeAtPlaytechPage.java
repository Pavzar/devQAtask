package org.pavzar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class LifeAtPlaytechPage extends BasePage {

    private static final String RESEARCH_AREAS_CSS = "#collapse-6-4-6 .accordion-body > ul > li > ul > li";
    private static final String ALL_JOBS_BUTTON_CSS = "a.yellow-button[href='https://www.playtechpeople.com/jobs-our/']";
    private static final String SEARCH_BLUE_BUTTON_CSS = ".jobs-filter-wrap .blue-button";

    public LifeAtPlaytechPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getResearchAreas() {
        return waitPresenceOfElements(By.cssSelector(RESEARCH_AREAS_CSS)).stream()
                .map(element -> element.getAttribute("textContent"))
                .toList();
    }

    public AllJobsPage visitAllJobsPage() {
        clickByLocator(By.cssSelector(ALL_JOBS_BUTTON_CSS));
        waitVisibilityOfElement(By.cssSelector(SEARCH_BLUE_BUTTON_CSS));

        return new AllJobsPage(driver);
    }
}