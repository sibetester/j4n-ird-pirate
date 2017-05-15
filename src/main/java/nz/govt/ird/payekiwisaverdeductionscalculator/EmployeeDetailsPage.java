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

    @FindBy(id = "n110")
    private WebElement selectSaverMemberDisabled;

    @FindBy(id = "n100")
    private WebElement selectSaverMemberEnabled;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal28")
    private WebElement employeeDeductions;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal30")
    private WebElement employerContributions;

    @FindBy(id = "n140")
    private WebElement selectFundMemberEnabled;

    @FindBy(id = "n150")
    private WebElement selectFundMemberDisabled;

    @FindBy(id = "qss3Interviews_PAYECalculatorUpdated_xintglobalglobal35")
    private WebElement fundEmployerContributions;


    public static EmployeeDetailsPage landAt() {
        EmployeeDetailsPage result = new EmployeeDetailsPage(WebDriverHolder.get());
        PageFactory.initElements(WebDriverHolder.get(), result);
        result.html = WebDriverHolder.get().findElement(By.tagName("html"));
        return result;
    }


    private EmployeeDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public SummaryPage enterPayDetails(String name, String reference, String tax, String frequency, String perPay, String amount, boolean isSaverMember, String deduction, String saverContributions, boolean isFundMember, String fundContributions) {

        yourName.sendKeys(name);
        referenceNumber.sendKeys(reference);
        (new Select(taxCode)).selectByValue(tax);

        (new Select(payFrequency)).selectByValue(frequency);
        salaryWagePerPay.sendKeys(perPay);
        selectAmountNetOrGross(amount);

        //selectSaverMember(isSaverMember);
        (new Select(employeeDeductions)).selectByValue(deduction);
        employerContributions.sendKeys(saverContributions);
        //selectFundMember(isFundMember);
        //fundEmployerContributions.sendKeys(fundContributions);

        continueButton.click();

        WebDriverUtils.waitForElementToLoad(html);
        return SummaryPage.landAt();
    }

    private void selectAmountNetOrGross(String amount) {
        if (amount.contentEquals("Gross")) {
            grossPay.click();
        } else if (amount.contentEquals("Net")) {
            netPay.click();
        } else {
            logger.warn("Invalid option for amount: " + amount);
        }
    }

    private void selectSaverMember(boolean isMember) {
        if (isMember) {
            selectSaverMemberEnabled.click();
        } else {
            selectSaverMemberDisabled.click();
        }

    }

    private void selectFundMember(boolean isMember) {
        if (isMember) {
            selectFundMemberEnabled.click();
        } else {
            selectFundMemberDisabled.click();
        }

    }


}
