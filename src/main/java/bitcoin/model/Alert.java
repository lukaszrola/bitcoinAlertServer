package bitcoin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Alert {
    private static final BigDecimal EMPTY_LIMIT = BigDecimal.ZERO;

    @EqualsAndHashCode.Include
    private final String alertName;
    private final BitcoinPrice limit;

    public static Alert anAlert(String alertName, BigDecimal limit, String currencyPair) {
        return new Alert(alertName, BitcoinPrice.anBitcoinPrice(limit, currencyPair));
    }

    public static Alert anTemplateAlert(String alertName) {
        return new Alert(alertName, BitcoinPrice.EMPTY_PRICE);
    }

}
