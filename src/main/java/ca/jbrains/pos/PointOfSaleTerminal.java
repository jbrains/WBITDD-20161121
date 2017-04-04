package ca.jbrains.pos;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class PointOfSaleTerminal {
    public static void main(String[] args) {
        new ConsumeTextCommands(
                new ParseCommandsByFilteringWhitespace(),
                new InterpretAllCommandsAsBarcodes(
                        new SellOneItemController(
                                new InMemoryCatalog(
                                        new HashMap<String, Price>() {{
                                            put("7070529026686", Price.cents(195));
                                        }}
                                ),
                                new WriterDisplay(
                                        new PrintWriter(
                                                new OutputStreamWriter(System.out),
                                                true
                                        )
                                )
                        )
                )
        ).consumeTextCommands(new InputStreamReader(System.in));
    }
}
