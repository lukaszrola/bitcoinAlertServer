package bitcoin.model;

        import org.knowm.xchange.currency.CurrencyPair;

        import java.math.BigDecimal;

public class BitcoinPrice {
    private final BigDecimal price;
    private final CurrencyPair currencyPair;

    private BitcoinPrice(BigDecimal price, CurrencyPair currencyPair) {
        this.price = price;
        this.currencyPair = currencyPair;
    }

    public static BitcoinPrice anBitcoinPrice(BigDecimal price, CurrencyPair currencyPair) {
        return new BitcoinPrice(price, currencyPair);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }
}
