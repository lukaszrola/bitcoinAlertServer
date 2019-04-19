package bitcoin.batch;

import bitcoin.model.BitcoinPrice;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class BitcoinCurrentPriceProviderIntegrationTest {
private final BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider = new BitcoinCurrentPriceProvider();

    @Test
    void shouldGetLatesPriceInUSD() throws IOException {
        BitcoinPrice lastPrice = bitcoinCurrentPriceProvider.getLastPrice(CurrencyPair.BTC_USD);

        assertThat(lastPrice).isNotNull();
        assertThat(lastPrice.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
        assertThat(lastPrice.getPrice()).isNotNull();
    }
}