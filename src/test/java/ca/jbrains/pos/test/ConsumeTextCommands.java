package ca.jbrains.pos.test;

import java.io.Reader;

public class ConsumeTextCommands {

    private ParseCommandsByFilteringWhitespace parseCommandsByFilteringWhitespace;
    private InterpretAllCommandsAsBarcodes interpretAllCommandsAsBarcodes;

    public ConsumeTextCommands(ParseCommandsByFilteringWhitespace parseCommandsByFilteringWhitespace, InterpretAllCommandsAsBarcodes interpretAllCommandsAsBarcodes) {
        this.parseCommandsByFilteringWhitespace = parseCommandsByFilteringWhitespace;
        this.interpretAllCommandsAsBarcodes = interpretAllCommandsAsBarcodes;
    }

    public void consumeTextCommands(Reader commandSource) {
        this.parseCommandsByFilteringWhitespace.parseCommands(commandSource)
                .forEach(this.interpretAllCommandsAsBarcodes::interpretCommand);
    }
}