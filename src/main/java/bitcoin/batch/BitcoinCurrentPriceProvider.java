package bitcoin.batch;

import bitcoin.model.BitcoinPrice;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SuppressWarnings("SpellCheckingInspection")
@Component
class BitcoinCurrentPriceProvider {
    @SuppressWarnings("SpellCheckingInspection")
    private final Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    private final MarketDataService marketDataService = bitstamp.getMarketDataService();

    BitcoinPrice getLastPrice(String currencyPair) throws IOException {
        Ticker ticker = marketDataService.getTicker(new CurrencyPair(currencyPair));
        return BitcoinPrice.anBitcoinPrice(ticker.getLast(), currencyPair);
    }
}
