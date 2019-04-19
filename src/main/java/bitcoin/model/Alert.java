package bitcoin.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Alert {
    private static final BigDecimal EMPTY_LIMIT = BigDecimal.ZERO;

    @EqualsAndHashCode.Include
    private final String alertName;

    private final BitcoinPrice limit;

    @Setter
    private AlertState state;

    public static Alert anAlert(String alertName, BigDecimal limit, String currencyPair) {
        return new Alert(alertName, BitcoinPrice.anBitcoinPrice(limit, currencyPair), AlertState.UNDEFINED);
    }

    public static Alert anTemplateAlert(String alertName) {
        return new Alert(alertName, BitcoinPrice.EMPTY_PRICE, AlertState.UNDEFINED);
    }

}
