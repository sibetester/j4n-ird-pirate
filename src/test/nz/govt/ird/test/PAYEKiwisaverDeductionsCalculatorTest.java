package nz.govt.ird.test;

import nz.co.bnz.webdriver.WebDriverExternalResource;
import nz.govt.ird.payekiwisaverdeductionscalculator.Assertions;
import nz.govt.ird.payekiwisaverdeductionscalculator.SummaryPage;
import nz.govt.ird.payekiwisaverdeductionscalculator.UserAndTaxYearPage;

import org.junit.ClassRule;
import org.junit.Test;

public class PAYEKiwisaverDeductionsCalculatorTest {

    @ClassRule
    public static WebDriverExternalResource driverResource = new WebDriverExternalResource();

    @Test
    public void testEmployeePAYE() {

        SummaryPage summaryPage = UserAndTaxYearPage.navigateTo()
            .enterUserAndTaxDetails("Employee", "1 April 2016 to 31 March 2017")
            .enterPayDetails("Test User", "TEST1234", "M", "Weekly", "1,024.00", "Net");

        Assertions.assertThat(summaryPage)
            .hasName("Test User")
            .hasReferenceNumber("TEST1234")
            .hasTaxCode("M")
            .hasPayFrequency("Weekly")
            .hasPayAmount("$1,024.00 net");
    }

}
