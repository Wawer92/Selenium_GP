package Selenium_cucumber;

import cucumber.api.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.NumberFormat;
import java.util.Random;





public class Main_test {

    private WebDriver driver;
    private static final String DRIVER_LOCATION = "E:\\geckodriver.exe";
    private static final String BASE_URL = "http://sports.williamhill.com/betting/en-gb";
    private static final String FOOTBALL_XPATH = "(//*[@id='nav-football']/a)[1]";
    private static final String EVENTS_XPATH = "(//*[@class='btmarket__wrapper clickable-selections -expanded'])[1]";
    private static final String BET = "(//*[@class='btmarket__wrapper -expanded'])[1]";
    private static final String buttonpath = "(//*[@id='markets-container']/div/section/div/div/div/div/button)[1]";
    private static final String bet = "(//*[@id='bets-container-singles']/div/div/div/span/input)[1]";
    private static final String odds = "(//*[@id='bets-container-singles']/div/header[2]/span/selection-price/span)[1]";
    private String value;
    private double cash_int;
    private String eventXpath;

    @Given("^We have website http://sports\\.williamhill\\.com/betting/en-gb$")
    public void we_have_website_http_sports_williamhill_com_betting_en_gb() throws Throwable {

        System.setProperty("webdriver.gecko.driver", DRIVER_LOCATION);
        this.driver = new FirefoxDriver();
        driver.manage().window().maximize(); // maximize browser window

        // Getting base page
        driver.get(BASE_URL);
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url,BASE_URL);

    }

    @When("^Navigate to any football event$")
    public void navigate_to_any_football_event() throws Throwable {

        // Clicking on FootBall
        clickLink(By.xpath(FOOTBALL_XPATH));
        // Waiting for page reload
        waitForSeconds(5);
        // Checking if page reloaded
        waitForVisibilityOfElement(By.xpath(EVENTS_XPATH));
        String url_events = driver.getCurrentUrl();
        Assert.assertEquals(url_events,"http://sports.williamhill.com/betting/en-gb/football");
        // Creating locator basaed on random
        eventXpath = "(//*[@class='event']/div/div/ul/li/a)"+"["+rnd()+"]";
        // Clicking randomized event
        clickLink(By.xpath(eventXpath));
        // Waiting to see results
        waitForSeconds(5);
    }

    @When("^Select event and place a \"([^\"]*)\" bet for the home team to Win$")
    public void select_event_and_place_a_bet_for_the_home_team_to_Win(String cash) throws Throwable {

        waitForVisibilityOfElement(By.xpath(buttonpath));
        Assert.assertTrue(driver.findElement(By.xpath(buttonpath)).isDisplayed());
        value = driver.findElement(By.xpath(buttonpath)).getAttribute("data-odds");
        clickLink(By.xpath(buttonpath));
        waitForVisibilityOfElement(By.xpath(bet));
        cash_int = Double.parseDouble(cash);
        driver.findElement(By.xpath(bet)).sendKeys(cash);
        waitForSeconds(4);
    }

    @When("^Place bet and assert the odds and returns offered$")
    public void place_bet_and_assert_the_odds_and_returns_offered() throws Throwable {

        String vvv1 = driver.findElement(By.xpath(odds)).getText();
        int a = ((Number) NumberFormat.getInstance().parse(value)).intValue();
        double an = (double) a;
        double b = Double.parseDouble(value.substring(value.indexOf("/")+1));
        double c = (an/b);
        double d = Math.round(c*100.0)/100.0;
        double chance = Math.floor(((c*cash_int)+cash_int)*100.0)/100.0;
        Assert.assertEquals(vvv1,value);
        String aaa = driver.findElement(By.id("total-to-return-price")).getText();
        Assert.assertEquals(Double.toString(chance),aaa);

    }

    @Then("^Close the browser$")
    public void close_the_browser() throws Throwable {
        driver.quit();

    }

    private int rnd(){
        Random rand = new Random();


        int randomNum = rand.nextInt(5) + 1;

        return randomNum;
    }


    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e){
            System.out.println("Error\n"+e);
        }
    }

    private void waitForVisibilityOfElement(By locator){
        (new WebDriverWait(driver, 15)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void clickLink(By locator){
        (new WebDriverWait(driver, 15)).until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(locator));
    }


}
