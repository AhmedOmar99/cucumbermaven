package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleSearchTest {
    public static void main(String[] args) {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Navigate to Google
        driver.get("https://www.google.com");

        // Find the search box element by name
        WebElement searchBox = driver.findElement(By.name("q"));

        // Type the search query
        searchBox.sendKeys("kooora");

        // Submit the form
        searchBox.submit();

        // Wait for a few seconds (you can use explicit waits for better synchronization)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the title of the search results page
        System.out.println("Page title: " + driver.getTitle());

        // Close the browser
        driver.quit();
    }
}
