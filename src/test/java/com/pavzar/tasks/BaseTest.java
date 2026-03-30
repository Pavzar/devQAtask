package com.pavzar.tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseTest {
    protected WebDriver driver;
    protected StringBuilder testResults;
    private TestInfo testInfo;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        this.testInfo = testInfo;

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        testResults = new StringBuilder();
    }

    @AfterEach
    public void tearDown() {
        try {
            Path path = Paths.get("testResults");
            Files.createDirectories(path);

            String testName = this.testInfo.getTestMethod()
                    .map(Method::getName)
                    .orElse("unknownTest");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path filePath = path.resolve("test_" + testName + "_" + timestamp + ".txt");

            Files.writeString(filePath, testResults.toString(), StandardCharsets.UTF_8);

            System.out.println(testResults);
            System.out.println("Test results written to: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error writing test results to file: " + e.getMessage());
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
