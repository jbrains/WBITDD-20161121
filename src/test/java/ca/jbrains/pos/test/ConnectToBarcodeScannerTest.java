package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConnectToBarcodeScannerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    private BarcodeScannedListener barcodeScannedListener;

    // REFACTOR Isn't there a library function for this?!
    // RISK Should there be a trailing line separator here?
    public static String unlines(List<String> linesOfText) {
        return String.join(System.lineSeparator(), linesOfText);
    }

    @Test
    public void oneBarcodeCommand() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode::");
        }});

        consumeListOfTextCommands(Collections.singletonList("::barcode::"));
    }

    @Test
    public void noBarcodeCommands() throws Exception {
        context.checking(new Expectations() {{
            never(barcodeScannedListener);
        }});

        consumeListOfTextCommands(Collections.emptyList());
    }

    @Test
    public void severalBarcodeCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 4::");
        }});

        consumeListOfTextCommands(Arrays.asList(
                "::barcode 1::",
                "::barcode 2::",
                "::barcode 3::",
                "::barcode 4::"
        ));
    }

    private void consumeListOfTextCommands(List<String> textCommands) {
        consumeTextCommand(new StringReader(unlines(textCommands)));
    }

    private void consumeTextCommand(Reader commandSource) {
        new BufferedReader(commandSource).lines().forEach(barcodeScannedListener::onBarcode);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
