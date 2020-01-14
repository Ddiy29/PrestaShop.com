package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class MainPage extends BasePage {

    public MainPage(BaseTest testClass) {
        super(testClass);
    }

    public boolean checkCurrencies(String currency) {
        String xpathAllCurrencies = "//div[@class='currency-selector dropdown js-dropdown']//span[2][contains(text(), '%s')]";
        String xpathAllProduct = "//div[@class='products']//span[contains(text(), '%s')]";
        WebElement currencies = testClass.getDriver().findElement(By.xpath(String.format(xpathAllCurrencies, currency)));
        List<WebElement> prices = testClass.getDriver().findElements(By.xpath(String.format(xpathAllProduct, currency)));
        for (WebElement price : prices) {
            if (price.getText().contains(currency) & currencies.getText().contains(currency)) {
                return true;
            }
        }
        return false;
    }
}
