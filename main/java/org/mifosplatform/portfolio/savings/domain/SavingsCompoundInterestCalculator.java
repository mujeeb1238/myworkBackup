package org.mifosplatform.portfolio.savings.domain;

import java.math.BigDecimal;
import java.util.List;

public interface SavingsCompoundInterestCalculator {

    BigDecimal calculate(List<SavingsAccountDailyBalance> dailyBalances, Integer numberOfDays);

}