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

public class UserAndTaxYearPage {

    private static final Logger logger = LoggerFactory.getLogger(UserAndTaxYearPage.class);

    private WebDriver driver;

    private WebElement html;

    @FindBy(id = "n11")
    private WebElement employeeOption;

    @FindBy(id = "n12")
    private WebElement employerOption;

    @FindBy(id = "qss2Interviews_PAYECalculatorUpdated_xintglobalglobal7")
    private WebElement taxPeriod;

    @FindBy(id = "submit")
    private WebElement continueButton;

    public static UserAndTaxYearPage navigateTo() {
        String linkToCalculator = "http://brc.ird.govt.nz/web-determinations/startsession/PAYE_Calculator/en-GB/Attribute~b1%40Rules_ProceduralRules_VisibilityRules_doc~global~global";
        WebDriverHolder.get().get(linkToCalculator);
        WebDriverUtils.waitForElementToAppear(By.id("n11"));
        return landAt();
    }

    public static UserAndTaxYearPage landAt() {
        UserAndTaxYearPage result = new UserAndTaxYearPage(WebDriverHolder.get());
        PageFactory.initElements(WebDriverHolder.get(), result);
        result.html = WebDriverHolder.get().findElement(By.tagName("html"));
        return result;
    }

    private UserAndTaxYearPage(WebDriver driver) {
        this.driver = driver;
    }

    public EmployeeDetailsPage enterUserAndTaxDetails(String user, String period) {
        selectUser(user);
        selectTaxPeriod(period);
        continueButton.click();

        WebDriverUtils.waitForElementToLoad(html);
        return EmployeeDetailsPage.landAt();
    }

    private void selectUser(String user) {
        if (user.contentEquals("Employee")) {
            employeeOption.click();
        }
        else if (user.contentEquals("Employer")) {
            employerOption.click();
        }
        else {
            logger.warn("Invalid option for user: " + user);
        }
    }

    private void selectTaxPeriod(String period) {
        (new Select(taxPeriod)).selectByValue(period);
    }

}
