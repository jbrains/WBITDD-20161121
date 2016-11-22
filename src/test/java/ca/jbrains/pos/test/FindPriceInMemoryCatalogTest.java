package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);
        Assert.assertEquals(
                matchingPrice,
                catalogWith("12345", matchingPrice).findPrice("12345"));
    }

    private Catalog catalogWith(String barcode, Price matchingPrice) {
        return new InMemoryCatalog(Collections.singletonMap(barcode, matchingPrice));
    }

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(null, catalogWithout("12345").findPrice("12345"));
    }

    private Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(Collections.emptyMap());
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
