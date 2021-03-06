package bitcoin.model;

import lombok.*;

import java.math.BigDecimal;

@SuppressWarnings("SpellCheckingInspection")
@Data
public class BitcoinPrice {
    public static final BitcoinPrice EMPTY_PRICE = new BitcoinPrice(null, "");

    private final BigDecimal price;
    private final String currencyPair;

    public static BitcoinPrice anBitcoinPrice(BigDecimal price, String currencyPair) {
        return new BitcoinPrice(price, formatCurrency(currencyPair));
    }

    private static String formatCurrency(String currencyPair) {
        return currencyPair.replace('-', '/');
    }

    public boolean priceExceededLimit(BitcoinPrice limit){
        return limit.getPrice().compareTo(price) < 0;
    }
}
