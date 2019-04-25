package bitcoin.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Alert {
    private static final BigDecimal EMPTY_LIMIT = BigDecimal.ZERO;

    @EqualsAndHashCode.Include
    private final String alertName;

    private final BitcoinPrice limit;

    private AlertState state;

    private Instant timestamp;

    public static Alert anAlert(String alertName, BigDecimal limit, String currencyPair) {
        return new Alert(alertName, BitcoinPrice.anBitcoinPrice(limit, currencyPair), AlertState.UNDEFINED, Instant.now());
    }

    public static Alert anTemplateAlert(String alertName) {
        return new Alert(alertName, BitcoinPrice.EMPTY_PRICE, AlertState.UNDEFINED, Instant.now());
    }

}
