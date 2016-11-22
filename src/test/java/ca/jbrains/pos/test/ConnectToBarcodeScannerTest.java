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

    @Test
    public void oneBarcodeCommand() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode::");
        }});

        consumeTextCommand(new StringReader(unlines(Collections.singletonList("::barcode::"))));
    }

    private void consumeTextCommand(Reader commandSource) throws IOException {
        barcodeScannedListener.onBarcode(new BufferedReader(commandSource).readLine());
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }

    // REFACTOR Isn't there a library function for this?!
    public static String unlines(List<String> linesOfText) {
        return String.join(System.lineSeparator(), linesOfText);
    }
}
