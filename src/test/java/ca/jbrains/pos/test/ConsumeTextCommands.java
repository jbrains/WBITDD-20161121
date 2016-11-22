package ca.jbrains.pos.test;

import java.io.Reader;

public class ConsumeTextCommands {

    private ParseCommands parseCommands;
    private InterpretCommands interpretCommands;

    public ConsumeTextCommands(ParseCommands parseCommands, InterpretCommands interpretCommands) {
        this.parseCommands = parseCommands;
        this.interpretCommands = interpretCommands;
    }

    public void consumeTextCommands(Reader commandSource) {
        this.parseCommands.parseCommands(commandSource)
                .forEach(this.interpretCommands::interpretCommand);
    }
}