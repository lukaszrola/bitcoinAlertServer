package bitcoin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Alert {
    private static final BigDecimal EMPTY_LIMIT = BigDecimal.ZERO;
    public static final String EMPTY_CURRENCY = "";

    @EqualsAndHashCode.Include
    private final String alertName;
    private final BigDecimal priceLimit;
    private final String currencyPair;

    public static Alert anAlert(String alertName, BigDecimal limit, String currencyPair){
        return new Alert(alertName,limit, formatCurrency(currencyPair));
    }

    private static String formatCurrency(String currencyPair) {
        return currencyPair.replace('-', '/');
    }

    public static Alert anTemplateAlert(String alertName){
        return new Alert(alertName, EMPTY_LIMIT, EMPTY_CURRENCY);
    }

}
