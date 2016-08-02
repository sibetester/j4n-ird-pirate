package nz.govt.ird.payekiwisaverdeductionscalculator;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions {

  /**
   * Creates a new instance of <code>{@link nz.govt.ird.payekiwisaverdeductionscalculator.EmployeeDetailsPageAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static nz.govt.ird.payekiwisaverdeductionscalculator.EmployeeDetailsPageAssert assertThat(nz.govt.ird.payekiwisaverdeductionscalculator.EmployeeDetailsPage actual) {
    return new nz.govt.ird.payekiwisaverdeductionscalculator.EmployeeDetailsPageAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link nz.govt.ird.payekiwisaverdeductionscalculator.SummaryPageAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static nz.govt.ird.payekiwisaverdeductionscalculator.SummaryPageAssert assertThat(nz.govt.ird.payekiwisaverdeductionscalculator.SummaryPage actual) {
    return new nz.govt.ird.payekiwisaverdeductionscalculator.SummaryPageAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link nz.govt.ird.payekiwisaverdeductionscalculator.UserAndTaxYearPageAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static nz.govt.ird.payekiwisaverdeductionscalculator.UserAndTaxYearPageAssert assertThat(nz.govt.ird.payekiwisaverdeductionscalculator.UserAndTaxYearPage actual) {
    return new nz.govt.ird.payekiwisaverdeductionscalculator.UserAndTaxYearPageAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
