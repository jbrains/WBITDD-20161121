package ca.jbrains.pos.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class ParseCommands {
    public ParseCommands() {
    }

    public Stream<String> parseCommands(Reader commandSource) {
        return sanitizeCommands(new BufferedReader(commandSource).lines());
    }

    public Stream<String> sanitizeCommands(Stream<String> commandStream) {
        return commandStream
                .map(String::trim)
                .filter((line) -> !line.isEmpty());
    }
}