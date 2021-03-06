package ca.jbrains.pos.test;

import ca.jbrains.text.Text;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

public class DisplayToWriterTest {
    @Test
    public void emptyBarcode() throws Exception {
        final StringWriter canvas = new StringWriter();
        new WriterDisplay(new PrintWriter(canvas)).displayScannedEmptyBarcodeMessage();
        Assert.assertEquals(
                Collections.singletonList("Scanning error: empty barcode"),
                Text.lines(canvas.toString()));
    }

    @Test
    public void productNotFoundMessage() throws Exception {
        final StringWriter canvas = new StringWriter();
        new WriterDisplay(new PrintWriter(canvas)).displayProductNotFoundMessage("99999");
        Assert.assertEquals(
                Collections.singletonList("Product not found for 99999"),
                Text.lines(canvas.toString()));
    }

    @Test
    public void price() throws Exception {
        // This test shows how "format price" fits into the "flow"
        // of "display price", even if that "flow" is quite small.
        // We have put the detailed tests for formatting a price
        // elsewhere.
        final StringWriter canvas = new StringWriter();
        new WriterDisplay(new PrintWriter(canvas)).displayPrice(Price.cents(750));
        Assert.assertEquals(
                Collections.singletonList("EUR 7.50"),
                Text.lines(canvas.toString()));
    }

    public static class WriterDisplay {
        private final PrintWriter out;

        public WriterDisplay(PrintWriter out) {
            this.out = out;
        }

        public void displayScannedEmptyBarcodeMessage() {
            out.println(String.format("Scanning error: empty barcode"));
        }

        public void displayProductNotFoundMessage(String barcodeNotFound) {
            out.println(String.format("Product not found for %s", "99999"));
        }

        public void displayPrice(Price price) {
            out.println(new EnglishLanguageMessageFormat().format(price));
        }
    }

}
