package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

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
        consumeTextCommand(new StringReader(unlines(textCommands)));
    }

    public void consumeTextCommand(Reader commandSource) {
        consumeTextCommands(this::parseCommands, this::interpretCommand, commandSource);
    }

    public void consumeTextCommands(
            Function<Reader, Stream<String>> parser,
            Consumer<String> interpreter,
            Reader commandSource) {

        parser.apply(commandSource).forEach(interpreter);
    }

    public Stream<String> parseCommands(Reader commandSource) {
        return sanitizeCommands(new BufferedReader(commandSource).lines());
    }

    public void interpretCommand(String commandText) {
        barcodeScannedListener.onBarcode(commandText);
    }

    public Stream<String> sanitizeCommands(Stream<String> commandStream) {
        return commandStream
                .map(String::trim)
                .filter((line) -> !line.isEmpty());
    }

    public interface BarcodeScannedListener {
        void onBarcode(String barcode);
    }
}
