package ca.jbrains.pos.test;

import ca.jbrains.pos.BarcodeScannedListener;
import ca.jbrains.pos.ConsumeTextCommands;
import ca.jbrains.pos.InterpretAllCommandsAsBarcodes;
import ca.jbrains.pos.ParseCommandsByFilteringWhitespace;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

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

    @Test
    public void rejectEmptyCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 4::");
        }});

        consumeListOfTextCommands(Arrays.asList(
                "",
                "",
                "",
                "::barcode 1::",
                "",
                "::barcode 2::",
                "",
                "",
                "::barcode 3::",
                "",
                "::barcode 4::",
                "",
                ""
        ));
    }

    @Test
    public void ignoreWhitespaceInCommands() throws Exception {
        context.checking(new Expectations() {{
            oneOf(barcodeScannedListener).onBarcode("::barcode 1::");
            oneOf(barcodeScannedListener).onBarcode("::barcode\t2::");
            oneOf(barcodeScannedListener).onBarcode("::barcode 3::");
            oneOf(barcodeScannedListener).onBarcode("::barcode\t\t4::");
        }});

        consumeListOfTextCommands(Arrays.asList(
                "     \t       ",
                "          ::barcode 1::     ",
                "     \t   ",
                "  ::barcode\t2::\t\t   ",
                " ",
                "    ",
                " ::barcode 3::\t",
                "",
                "\t::barcode\t\t4::    ",
                "\t\t\t",
                ""
        ));
    }

    private void consumeListOfTextCommands(List<String> textCommands) {
        new ConsumeTextCommands(
                new ParseCommandsByFilteringWhitespace(),
                new InterpretAllCommandsAsBarcodes(barcodeScannedListener))
                .consumeTextCommands(
                        new StringReader(unlines(textCommands)));
    }
}
