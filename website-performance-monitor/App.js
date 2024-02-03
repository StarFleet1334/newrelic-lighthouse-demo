const { Builder, By, Key, until } = require("selenium-webdriver");
const assert = require("assert");

(async function example() {
    let driver;
    try {
        driver = await new Builder()
            .forBrowser("chrome")
            .build();

        await driver.get('https://one.eu.newrelic.com/');

        // Wait for a certain amount of time (e.g., 30 seconds)
        await driver.sleep(5000); // Sleep for 30,000 milliseconds = 30 seconds

        // Typing Email
        await driver.findElement(By.id("login_email")).sendKeys("ilataria@solvd.com");

        await driver.sleep(5000); // Sleep for 30,000 milliseconds = 30 seconds

        await driver.findElement(By.id("login_submit")).click()

        await driver.findElement(By.id("login_password")).sendKeys("Iliko20022005");

        await driver.sleep(5000); // Sleep for 30,000 milliseconds = 30 seconds

        await driver.findElement(By.id("login_submit")).click()

        await driver.sleep(5000); // Sleep for 30,000 milliseconds = 30 seconds







    } catch (error) {
        console.error('Error occurred:', error);
    } finally {
        if (driver) {
            await driver.quit(); // This will still close the browser after the sleep
        }
    }
})();
