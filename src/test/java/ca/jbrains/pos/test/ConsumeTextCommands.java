package ca.jbrains.pos.test;

import java.io.Reader;

public class ConsumeTextCommands {
    private ParseCommands parseCommands;
    private InterpretCommand interpretCommand;

    public ConsumeTextCommands(ParseCommands parseCommands, InterpretCommand interpretCommand) {
        this.parseCommands = parseCommands;
        this.interpretCommand = interpretCommand;
    }

    public void consumeTextCommands(Reader commandSource) {
        this.parseCommands.parseCommands(commandSource)
                .forEach(this.interpretCommand::interpretCommand);
    }
}