package bitcoin.batch;

import bitcoin.model.LastPrice;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("SpellCheckingInspection")
class BitcoinCurrentPriceProviderIntegrationTest {
    private static final String SOME_CURRENCY = CurrencyPair.BTC_USD.toString();
    private final BitcoinCurrentPriceProvider bitcoinCurrentPriceProvider = new BitcoinCurrentPriceProvider();

    @Test
    void shouldGetLatestPriceInUSD() throws IOException {
        LastPrice lastPrice = bitcoinCurrentPriceProvider.getLastPrice(SOME_CURRENCY);

        assertThat(lastPrice).isNotNull();
        assertThat(lastPrice.getUpdateTime()).isNotNull();
        assertThat(lastPrice.getBitcoinPrice().getCurrencyPair()).isEqualTo(SOME_CURRENCY);
    }
}