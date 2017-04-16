package nz.govt.ird.payekiwisaverdeductionscalculator;

import nz.co.bnz.webdriver.WebDriverHolder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SummaryPage {

    private WebDriver driver;

    private WebElement html;

    @FindBy(css = "#summary85 > div > table > tbody > tr:nth-child(1) > td:nth-child(2)")
    private WebElement yourName;

    @FindBy(css = "#summary85 > div > table > tbody > tr:nth-child(2) > td:nth-child(2)")
    private WebElement referenceNumber;

    @FindBy(css = "#summary85 > div > table > tbody > tr:nth-child(3) > td:nth-child(2)")
    private WebElement taxCode;

    @FindBy(css = "#summary96 > div > table > tbody > tr:nth-child(1) > td:nth-child(2)")
    private WebElement payFrequency;

    @FindBy(css = "#summary96 > div > table > tbody > tr:nth-child(3) > td:nth-child(2)")
    private WebElement payAmount;

    @FindBy(css = "#summary104 > div > table > tbody > tr:nth-child(2) > td:nth-child(3)")
    private WebElement calculationPAYE;

    public static SummaryPage landAt() {
        SummaryPage result = new SummaryPage(WebDriverHolder.get());
        PageFactory.initElements(WebDriverHolder.get(), result);
        result.html = WebDriverHolder.get().findElement(By.tagName("html"));
        return result;
    }

    private SummaryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getName() {
        return yourName.getText();
    }

    public String getReferenceNumber() {
        return referenceNumber.getText();
    }

    public String getTaxCode() {
        return taxCode.getText();
    }

    public String getPayFrequency() {
        return payFrequency.getText();
    }

    public String getPayAmount() {
        return payAmount.getText();
    }

    public String getCalculationPAYE() {
        return calculationPAYE.getText();
    }
}
