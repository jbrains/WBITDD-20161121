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

    private void consumeListOfTextCommands(List<String> textCommands) throws IOException {
        consumeTextCommand(new StringReader(unlines(textCommands)));
    }

    private void consumeTextCommand(Reader commandSource) throws IOException {
        final String line = new BufferedReader(commandSource).readLine();
        if (line != null)
            barcodeScannedListener.onBarcode(line);
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
