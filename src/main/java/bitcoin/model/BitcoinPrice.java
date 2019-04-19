package bitcoin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BitcoinPrice {
    private final BigDecimal price;
    private final CurrencyPair currencyPair;

    public static BitcoinPrice anBitcoinPrice(BigDecimal price, CurrencyPair currencyPair) {
        return new BitcoinPrice(price, currencyPair);
    }
}
