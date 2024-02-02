const { Builder, By, Key, until } = require('selenium-webdriver');

(async function example() {
    let driver = await new Builder()
        .forBrowser('chrome')
        .usingServer('http://localhost:4444/wd/hub')
        .withCapabilities({ 'google:chromeOptions': { 'args': ['--no-sandbox'] } })
        .build();
    try {
        await driver.get('http://www.google.com/');
        await driver.findElement(By.name('q')).sendKeys('webdriver', Key.RETURN);
        await driver.wait(until.titleIs('webdriver - Google Search'), 1000);
    } finally {
        await driver.quit();
    }
})();
