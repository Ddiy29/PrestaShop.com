package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import pages.MainPage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

abstract public class BaseTest {

    protected WebDriver driver;
    protected static Actions actions;
    private static final String WEB_URL = "http://prestashop-automation.qatestlab.com.ua/ru/";

    public WebDriver getDriver() {
        return driver;
    }

    private void setDriverPathByOs() {
        String osDriverName;
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            osDriverName = "mac64";
        } else if (os.contains("win")) {
            osDriverName = "win_32.exe";
        } else {
            throw new RuntimeException("Cannot define your OS: " + os);
        }
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chrome_" + osDriverName);
    }

    @BeforeTest
    public void initializeDriver() {
        setDriverPathByOs();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1440, 900));
        turnOnImplicityWait();
        actions = new Actions(driver);
    }

    public MainPage openWebApp() {
        driver.get(WEB_URL);
        return new MainPage(this);
    }

    @AfterMethod
    public void testFailureMakeScreenshot(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) screenShot();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    protected void turnOnImplicityWait() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    protected void turnOffImplicityWait() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public WebElement waitTillByElementVisible(WebElement element) {
        turnOffImplicityWait();
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException s) {
            System.out.println(s);
        }
        finally {
            turnOnImplicityWait();
        }
        return element;
    }

    public WebElement clickByElementWithWait(WebElement element) {
        waitTillByElementVisible(element).click();
        return element;
    }

    public void screenShot() throws IOException {
        File scr = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String filename =  new SimpleDateFormat("yyyyMMddhhmmss'.txt'").format(new Date());
        File dest = new File("filePath/" + filename);
        FileUtils.copyFile(scr, dest);
    }

    public WebElement scrollToElementByJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

public WebElement waitAndScrollByElement(WebElement element) {
    waitTillByElementVisible(element);
    scrollToElementByJs(element);
    return element;
}

    public WebElement waitTillByElementInVisibilityText(WebElement element, String text) {
        turnOffImplicityWait();
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.invisibilityOfElementWithText((By) element,text));
        } catch (NoSuchElementException s) {
            System.out.println(s);
        }
        finally {
            turnOnImplicityWait();
        }
        return element;
    }


}
