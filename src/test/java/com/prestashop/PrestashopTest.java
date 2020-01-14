package com.prestashop;

import enums.Currency;
import enums.Sort;
import base.BaseTest;
import pages.SearchResultPage;
import pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PrestashopTest extends BaseTest {

    @Test
    public void checkAllPages() {
        MainPage mainPage = openWebApp();
        Assert.assertTrue(mainPage.checkCurrencies(Currency.UAH.getCurrency()));
        mainPage.setPrice(Currency.USD.getCurrency());
        SearchResultPage searchResultPage = mainPage.enterText("dress", SearchResultPage.class);
        Assert.assertEquals(searchResultPage.checkExpectedAmountOfProduct(7), searchResultPage.checkActualAmountOfProduct(), "The page is match amount found of products");
        Assert.assertTrue(searchResultPage.checkPriceOfProducts(Currency.USD.getCurrency()));
        searchResultPage.setSort(Sort.PRICE_FROM_HIGH_TILL_LOW.getValue());
        Assert.assertEquals(searchResultPage.checkExpectedSorted(), searchResultPage.checkActualSorted(), "Sort is match with sort on the page");
        Assert.assertEquals(searchResultPage.checkExpectedResultComparePriceWithDiscount(5), searchResultPage.checkActualResultComparePriceWithDiscount(5), "Price with discount is match with discount set on product");
    }
}

