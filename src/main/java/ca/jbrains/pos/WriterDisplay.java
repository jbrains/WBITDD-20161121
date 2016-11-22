package ca.jbrains.pos;

import ca.jbrains.pos.EnglishLanguageMessageFormat;
import ca.jbrains.pos.Price;

import java.io.PrintWriter;

public class WriterDisplay implements Display {
    private final PrintWriter out;

    public WriterDisplay(PrintWriter out) {
        this.out = out;
    }

    public void displayScannedEmptyBarcodeMessage() {
        out.println(String.format("Scanning error: empty barcode"));
    }

    public void displayProductNotFoundMessage(String barcodeNotFound) {
        out.println(String.format("Product not found for %s", barcodeNotFound));
    }

    public void displayPrice(Price price) {
        out.println(new EnglishLanguageMessageFormat().format(price));
    }
}
