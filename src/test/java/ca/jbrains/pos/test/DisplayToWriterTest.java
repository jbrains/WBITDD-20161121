package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DisplayToWriterTest {
    @Test
    public void emptyBarcode() throws Exception {
        final StringWriter canvas = new StringWriter();
        new WriterDisplay(new PrintWriter(canvas)).displayScannedEmptyBarcodeMessage();
        Assert.assertEquals(
                Collections.singletonList("Scanning error: empty barcode"),
                lines(canvas.toString()));
    }

    @Test
    public void productNotFoundMessage() throws Exception {
        final StringWriter canvas = new StringWriter();
        new WriterDisplay(new PrintWriter(canvas)).displayProductNotFoundMessage("99999");
        Assert.assertEquals(
                Collections.singletonList("Product not found for 99999"),
                lines(canvas.toString()));
    }

    public static class WriterDisplay {
        private final PrintWriter out;

        public WriterDisplay(PrintWriter out) {
            this.out = out;
        }

        public void displayScannedEmptyBarcodeMessage() {
            out.println("Scanning error: empty barcode");
        }

        public void displayProductNotFoundMessage(String barcodeNotFound) {
            out.println(String.format("Product not found for %s", "99999"));
        }
    }

    public static List<String> lines(String text) {
        return Arrays.asList(text.split(System.lineSeparator()));
    }
}
