package bitcoin.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BitcoinPriceTest {

    private static final String SOME_CURRENCY_PAIR = "BTC-USD";
    private static final String SOME_FORMATED_CURRENCY_PAIR = "BTC/USD";

    @Test
    void shouldFormatCurrency() {
        BitcoinPrice bitcoinPrice = BitcoinPrice.anBitcoinPrice(BigDecimal.ONE, SOME_CURRENCY_PAIR);

        assertThat(bitcoinPrice.getCurrencyPair()).isEqualTo(SOME_FORMATED_CURRENCY_PAIR);
    }

    @Test
    void priceNotExceededLimit() {
        BitcoinPrice bitcoinPrice = BitcoinPrice.anBitcoinPrice(BigDecimal.ONE, SOME_CURRENCY_PAIR);
        BitcoinPrice limit = BitcoinPrice.anBitcoinPrice(BigDecimal.TEN, SOME_CURRENCY_PAIR);

        assertThat(bitcoinPrice.priceExceededLimit(limit)).isFalse();
    }

    @Test
    void priceEqualToLimit() {
        BitcoinPrice bitcoinPrice = BitcoinPrice.anBitcoinPrice(BigDecimal.ONE, SOME_CURRENCY_PAIR);

        assertThat(bitcoinPrice.priceExceededLimit(bitcoinPrice)).isFalse();
    }

    @Test
    void priceExceededLimit() {
        BitcoinPrice bitcoinPrice = BitcoinPrice.anBitcoinPrice(BigDecimal.TEN, SOME_CURRENCY_PAIR);
        BitcoinPrice limit = BitcoinPrice.anBitcoinPrice(BigDecimal.ONE, SOME_CURRENCY_PAIR);

        assertThat(bitcoinPrice.priceExceededLimit(limit)).isTrue();
    }
}