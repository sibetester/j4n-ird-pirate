package nz.govt.ird.payekiwisaverdeductionscalculator;

import nz.co.bnz.webdriver.WebDriverHolder;
import nz.co.bnz.webdriver.WebDriverUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeDetailsPage {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsPage.class);

    private WebDriver driver;

    private WebElement html;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal7")
    private WebElement yourName;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal9")
    private WebElement referenceNumber;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal11")
    private WebElement taxCode;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal19")
    private WebElement payFrequency;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal20")
    private WebElement salaryWagePerPay;

    @FindBy(id = "n81")
    private WebElement grossPay;

    @FindBy(id = "n92")
    private WebElement netPay;

    @FindBy(id = "submit")
    private WebElement continueButton;

    public static EmployeeDetailsPage landAt() {
        EmployeeDetailsPage result = new EmployeeDetailsPage(WebDriverHolder.get());
        PageFactory.initElements(WebDriverHolder.get(), result);
        result.html = WebDriverHolder.get().findElement(By.tagName("html"));
        return result;
    }

    private EmployeeDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public SummaryPage enterPayDetails(String name, String reference, String tax, String frequency, String perPay, String amount) {

        yourName.sendKeys(name);
        referenceNumber.sendKeys(reference);
        (new Select(taxCode)).selectByValue(tax);

        (new Select(payFrequency)).selectByValue(frequency);
        salaryWagePerPay.sendKeys(perPay);
        selectAmountNetOrGross(amount);

        continueButton.click();

        WebDriverUtils.waitForElementToLoad(html);
        return SummaryPage.landAt();
    }

    private void selectAmountNetOrGross(String amount) {
        if (amount.contentEquals("Gross")) {
            grossPay.click();
        }
        else if (amount.contentEquals("Net")) {
            netPay.click();
        }
        else {
            logger.warn("Invalid option for amount: " + amount);
        }
    }
}
