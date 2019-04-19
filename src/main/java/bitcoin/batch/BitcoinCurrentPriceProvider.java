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

@Component
class BitcoinCurrentPriceProvider {
    private Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    private MarketDataService marketDataService = bitstamp.getMarketDataService();

    BitcoinPrice getLastPrice(CurrencyPair currencyPair) throws IOException {
        Ticker ticker = marketDataService.getTicker(currencyPair);
        return BitcoinPrice.anBitcoinPrice(ticker.getLast(),currencyPair);
    }
}
