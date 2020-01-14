package pages;

import base.BaseTest;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.InvocationTargetException;

abstract public class AbstractPage {

    protected BaseTest testClass;

    public AbstractPage(BaseTest testClass) {
        this.testClass = testClass;
        PageFactory.initElements(testClass.getDriver(), this);
    }

    protected <T extends AbstractPage> T createPage(Class<T> clazz) {
        return createPage(clazz, null);
    }

    protected <T extends AbstractPage> T createPage(Class<T> clazz, String param) {
        if (param != null)
            try {
                return clazz.getConstructor(BaseTest.class, String.class).newInstance(testClass, param);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot create object of class: " + clazz.getName() + ", with param: " + param);
            }
        else {
            try {
                return clazz.getConstructor(BaseTest.class).newInstance(testClass);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot create object of class: " + clazz.getName());
            }
        }
    }
}

