package demo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        driver.get(
                "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        Thread.sleep(5000);

        // Fill in the name
        WebElement name = driver.findElement(By.xpath("//input[@aria-describedby='i2 i3']"));
        name.sendKeys("Saurabh Kumar");
        Thread.sleep(5000);

        // Write down the statement with the current epoch time
        long epochTime = Instant.now().getEpochSecond();
        String message = "I want to be the best QA Engineer! " + epochTime;
        WebElement textfiled = driver.findElement(By.xpath("//textarea[@aria-label='Your answer']"));
        textfiled.sendKeys(message);
        Thread.sleep(5000);

        // Enter automation testing experience (assuming it's a radio button selection)
        WebElement experienceOption = driver.findElement(By.xpath("//div[@id='i13']//div[@class='AB7Lab Id5V1']"));
        experienceOption.click();
        Thread.sleep(5000);

        // Select Java, Selenium, and TestNG from the checkboxes
        List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@class='eBFwI']"));
        for (WebElement checkbox : checkboxes) {
            String checkboxText = checkbox.getText();
            if (checkboxText.equals("Java") || checkboxText.equals("Selenium") || checkboxText.equals("TestNG")) {
                checkbox.click();
                Thread.sleep(5000);
            }
        }

        // Select how to be addressed from dropdown
        WebElement addressDropdown = driver.findElement(By.xpath("//div[@class='e2CuFe eU809d']"));
        addressDropdown.click();
        Thread.sleep(5000);
        WebElement addressOption = driver
                .findElement(By.xpath("(//span[@class='vRMGwf oJeWuf'][normalize-space()='Mr'])[2]"));
        addressOption.click();
        Thread.sleep(5000);

        // Calculate the date 7 days ago and enter it
        LocalDate date7DaysAgo = LocalDate.now().minus(7, ChronoUnit.DAYS);
        String date7DaysAgoStr = date7DaysAgo.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        driver.findElement(By.xpath("//input[@type='date']")).sendKeys(date7DaysAgoStr);
        Thread.sleep(5000);

        // Provide the current time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // 24-hour format
        String currentTime = LocalDateTime.now().format(timeFormatter);
        String[] currentTimeParts = currentTime.split(":");
        String currentHour = currentTimeParts[0];
        String currentMinute = currentTimeParts[1];

        // Locate hour and minute input fields using appropriate XPath and input the
        // values
        WebElement hourField = driver.findElement(By.xpath("//input[@aria-label='Hour']"));
        WebElement minuteField = driver.findElement(By.xpath("//input[@aria-label='Minute']"));

        hourField.sendKeys(currentHour);
        minuteField.sendKeys(currentMinute);
        Thread.sleep(5000);

        // Change the URL of the tab to amazon.in and handle the popup
        driver.get("https://www.amazon.in");
        Thread.sleep(5000);

        // Handle popup (assuming it's an iframe popup)
        try {
            // Try handling it as an alert first
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            // No alert, try handling as an iframe popup
            try {
                WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@name, 'popup')]"));
                driver.switchTo().frame(iframe);
                WebElement popupCloseButton = driver.findElement(By.xpath("//button[@aria-label='Close']"));
                popupCloseButton.click();
                driver.switchTo().defaultContent(); // Switch back to the main content
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.out.println("Popup not found or another error occurred: " + ex.getMessage());
            }
        }

        // Return to the form and submit
        WebElement submitButton = driver.findElement(By.xpath("//span[contains(text(),'Submit')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        Thread.sleep(5000);

        // Print the success message on the console
        WebElement successMessage = driver
                .findElement(By.xpath("//span[text()='QA Assignment - Automate Google Forms']"));
        System.out.println("Form Submission Success: " + successMessage.getText());
        System.out.println("End Test case: testCase01");
    }

}
