package nz.co.bnz.webdriver;

import com.google.common.base.Preconditions;
import org.openqa.selenium.WebDriver;

public class WebDriverHolder {

    private static WebDriver driver;

    public static WebDriver get() {
        Preconditions.checkNotNull(driver, "Calling WebDriverHolder.get before driver has been set.");
        return driver;
    }

    public static void set(WebDriver driver) {
        WebDriverHolder.driver = driver;
    }

    public static void clear() {
        WebDriverHolder.driver = null;
    }

}
