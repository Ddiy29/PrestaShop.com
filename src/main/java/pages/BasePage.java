package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePage extends AbstractPage {

    public BasePage(BaseTest testClass) {
        super(testClass);
    }

    @FindBy(xpath = "//div[@class='currency-selector dropdown js-dropdown']//a[@class='hidden-sm-down']")
    private WebElement currenciesDropDown;

    @FindBy(xpath = "//input[@class='ui-autocomplete-input']")
    private WebElement inputField;

    public WebElement setPrice(String chooseCurrency) {
        String choosePrice = "//a[contains(text(), '%s')]";
        currenciesDropDown.click();
        WebElement choseElement = testClass.getDriver().findElement(By.xpath(String.format(choosePrice, chooseCurrency)));
        testClass.clickByElementWithWait(choseElement);
        return choseElement;
    }

    public <T extends AbstractPage> T enterText(String text, Class<T> createNewPage) {
        inputField.sendKeys(text + Keys.ENTER);
        return createPage(createNewPage);
    }


}
