package pages;

import base.BaseTest;
import models.ObjectPrice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultPage extends AbstractPage {


    public SearchResultPage(BaseTest testClass) {
        super(testClass);
    }


    @FindBy(xpath = "//div[@class='col-md-6 hidden-sm-down total-products']/p")
    private WebElement amountOfProduct;

    @FindBy(xpath = "//a[@class='select-title']")
    private WebElement listOfSort;

    @FindBy(xpath = "//div[@class='products row']//div[@class='product-price-and-shipping']//span[1]")
    private List<WebElement> prices;

    @FindBy(xpath = "//div[@class='products row']//span[@class='regular-price']/following-sibling::span[@class='price']")
    private WebElement priceWithDiscount;

    @FindBy(xpath = "//div[@class='products row']//span[@class='price']/preceding-sibling::span[@class='regular-price']")
    private  WebElement priceWithoutDiscount;


    public String checkExpectedAmountOfProduct(int amount) {
        String xpathForProducts = "//div[@class='col-md-6 hidden-sm-down total-products']/p[text()='Товаров: %d.']";
        WebElement element = testClass.getDriver().findElement(By.xpath(String.format(xpathForProducts, amount)));
        return element.getText();
    }

    public String checkActualAmountOfProduct() {
        return amountOfProduct.getText();
    }

    public boolean checkPriceOfProducts(String currency) {
        String xpathAllProduct = "//div[@class='products row']//span[contains(text(), '%s')]";
        List<WebElement> prices = testClass.getDriver().findElements(By.xpath(String.format(xpathAllProduct, currency)));
        for (WebElement price : prices) {
            if (price.getText().contains(currency)) {
                return true;
            }
        }
        return false;
    }

    public WebElement setSort(String sort) {
        String chooseOfSort = "//div[@class='dropdown-menu']//a[contains(text(), '%s')]";
        listOfSort.click();
        WebElement clickBySort = testClass.waitTillByElementVisible(testClass.getDriver().findElement(By.xpath(String.format(chooseOfSort, sort))));
        testClass.clickByElementWithWait(clickBySort);
        return clickBySort;
    }

    public List<Double> checkExpectedSorted() {
        new WebDriverWait(testClass.getDriver(),
                30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='products row']/article[1][@data-id-product='4']")));
        ArrayList<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            priceList.add(Double.parseDouble(price.getText().substring(0, 4).replace(",", ".")));
        }
        Collections.sort(priceList, Collections.reverseOrder());
        return priceList;
    }

    public List<Double> checkActualSorted() {
        ArrayList<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            priceList.add(Double.parseDouble(price.getText().substring(0, 4).replace(",", ".")));
        }
        return priceList;
    }

    public List<ObjectPrice> checkExpectedResultComparePriceWithDiscount(int discount) {
        int parseDiscount = 0;
        String xpathForDiscount = "//div[@class='products row']//span[contains(text(), '%d')]";
        WebElement discountPrice = testClass.getDriver().findElement(By.xpath(String.format(xpathForDiscount, discount)));
        double readWithoutDiscountPrice = Double.parseDouble(priceWithoutDiscount.getText().substring(0, 4).replace(",", "."));
        if (discountPrice.getText().contains("%")) {
            parseDiscount = Integer.parseInt(discountPrice.getText().replace("-", "").replace("%", ""));
        }
        double discountSize = (readWithoutDiscountPrice / 100) * parseDiscount;
        double priceWithDiscount = new BigDecimal(readWithoutDiscountPrice - discountSize).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<ObjectPrice> expectedResult = new ArrayList<>();
        expectedResult.add(new ObjectPrice(priceWithDiscount));
        return expectedResult;
    }

    public List<ObjectPrice> checkActualResultComparePriceWithDiscount(int discount) {
        List<ObjectPrice> actualResult = new ArrayList<>();
        if (discount == 5) {
            double actualPriceWithDiscount = Double.parseDouble(testClass.getDriver().findElement(By.xpath("//div[@class='products row']//article[@data-id-product='5']//span[@class='price']")).getText().substring(0, 4).replace(",", "."));
            actualResult.add(new ObjectPrice(actualPriceWithDiscount));
        } else if (discount == 20) {
            double actualPriceWithDiscount = Double.parseDouble(testClass.getDriver().findElement(By.xpath("//div[@class='products row']//article[@data-id-product='7']//span[@class='price']")).getText().substring(0, 4).replace(",", "."));
            actualResult.add(new ObjectPrice(actualPriceWithDiscount));
        }
        return actualResult;
    }
}

