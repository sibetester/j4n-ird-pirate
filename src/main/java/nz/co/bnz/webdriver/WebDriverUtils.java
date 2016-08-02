package nz.co.bnz.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {

    public static WebElement waitForElementToAppear(By by) {
        WebDriverWait wait = new WebDriverWait(WebDriverHolder.get(), 30);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static boolean waitForElementToHaveText(By by, String text) {
        WebDriverWait wait = new WebDriverWait(WebDriverHolder.get(), 30);
        return wait.until(ExpectedConditions.textToBe(by, text));
    }

    public static void waitForElementToLoad(WebElement html) {
        WebDriverWait wait = new WebDriverWait(WebDriverHolder.get(), 30);
        wait.until(ExpectedConditions.stalenessOf(html));
    }

}
