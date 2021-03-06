package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);
        Assert.assertEquals(
                matchingPrice,
                catalogWith("12345", matchingPrice).findPrice("12345"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(null, catalogWithout("12345").findPrice("12345"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
