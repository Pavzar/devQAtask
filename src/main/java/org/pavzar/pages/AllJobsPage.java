package org.pavzar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllJobsPage extends BasePage {

    private static final String JOBS_SECTION_ID = "jobs-section";
    private static final String ESTONIAN_JOB_LINK_CSS = "a.job-item[data-location='estonia']";
    private static final String JOB_LOCATION_CSS = "li[itemprop='jobLocation'] spl-job-location[formattedaddress]";

    public AllJobsPage(WebDriver driver) {
        super(driver);
    }

    public String getTartuTallinnAvailablePositionFromEstonia() {
        waitVisibilityOfElement(By.id(JOBS_SECTION_ID));

        // Get all estonian job links only
        List<WebElement> estonianJobLinkElements = waitPresenceOfElements(By.cssSelector(ESTONIAN_JOB_LINK_CSS));

        List<String> estonianJobLinks = new ArrayList<>();

        for (WebElement estonianJobsLinkElement : estonianJobLinkElements) {
            String href = estonianJobsLinkElement.getAttribute("href");
            if (href != null && !href.isBlank()) {
                estonianJobLinks.add(href);
            }
        }

        // Find the first job link for an available position in Estonia from both Tartu and Tallinn
        String tallinnTartuPositionLink = "";

        for (String estonianJobLink : estonianJobLinks) {
            visit(estonianJobLink);

            // Try/catch check in case page is broken, skip if yes after timeout
            try {
                String jobLocation = waitPresenceOfElement(By.cssSelector(JOB_LOCATION_CSS)).getAttribute("formattedaddress");

                if (jobLocation != null && !jobLocation.isBlank()) {
                    jobLocation = jobLocation.toLowerCase(Locale.ROOT);

                    if (jobLocation.contains("tartu") && jobLocation.contains("tallinn")) {
                        tallinnTartuPositionLink = estonianJobLink;
                        break;
                    }
                }
            } catch (TimeoutException e) {
                System.err.println(e.getMessage());
                System.err.println("Tried visiting link: " + estonianJobLink);
            }
        }

        return tallinnTartuPositionLink;
    }
}
