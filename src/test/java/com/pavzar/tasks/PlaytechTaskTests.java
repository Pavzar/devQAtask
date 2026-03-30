package com.pavzar.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pavzar.pages.AllJobsPage;
import org.pavzar.pages.HomePage;
import org.pavzar.pages.LifeAtPlaytechPage;

import java.util.List;

public class PlaytechTaskTests extends BaseTest {

    @Test
    public void mainTasksTest() {
        HomePage homePage = new HomePage(driver);

        // 1. Open a web browser at the URL https://www.playtechpeople.com
        homePage.visitHomePage();

        // 2. Find how many teams there are in the company and list them all.
        List<String> playtechTeams = homePage.getTeams();
        int playtechTeamsSize = playtechTeams.size();

        Assertions.assertAll("Teams",
                () -> Assertions.assertNotNull(playtechTeams, "Teams list should not be null"),
                () -> Assertions.assertFalse(playtechTeams.isEmpty(), "Teams list should not be empty"),
                () -> Assertions.assertEquals(11, playtechTeamsSize, "Teams list size is incorrect"),
                () -> Assertions.assertTrue(playtechTeams.stream().noneMatch(String::isBlank), "Teams should not be blank")
        );

        testResults.append("Found %d teams: %s.%n".formatted(playtechTeamsSize, String.join(", ", playtechTeams)));

        /* 3. From the „Who we are“ section under „Life at Playtech“,
         list the areas we conduct our own research in order to reduce gambling related harm. */
        LifeAtPlaytechPage lifeAtPlaytechPage = homePage.visitLifeAtPlaytechPage();
        List<String> researchAreas = lifeAtPlaytechPage.getResearchAreas();
        int researchAreasSize = researchAreas.size();

        List<String> expected = List.of(
                "risk analysis using data derived from the use of BetBuddy",
                "investigating product related risks such as slot game volatility, use of autoplay and the effects of stake limits",
                "encouraging the use of safer gambling tools"
        );

        Assertions.assertAll("Research Areas",
                () -> Assertions.assertNotNull(researchAreas, "Research areas list should not be null"),
                () -> Assertions.assertFalse(researchAreas.isEmpty(), "Research areas list should not be empty"),
                () -> Assertions.assertEquals(3, researchAreasSize, "Research areas list size is incorrect"),
                () -> Assertions.assertTrue(researchAreas.stream().noneMatch(String::isBlank), "Research areas should not blank"),
                () -> Assertions.assertEquals(expected, researchAreas)
        );

        testResults.append("Found %d areas:%n%s%n"
                .formatted(researchAreasSize, String.join("\n", researchAreas)));

        // 4. Under „All Jobs“, print out a link for an available position in Estonia from both Tartu and Tallinn.
        AllJobsPage allJobsPage = lifeAtPlaytechPage.visitAllJobsPage();

        String availablePositionLink = allJobsPage.getTartuTallinnAvailablePositionFromEstonia();

        Assertions.assertAll("Tartu and Tallinn available position from Estonia",
                () -> Assertions.assertNotNull(availablePositionLink, "Link should not be null"),
                () -> Assertions.assertFalse(availablePositionLink.isBlank(), "Link should not be blank")
        );

        testResults.append("A link of an available position: %s".formatted(availablePositionLink));
    }
}
