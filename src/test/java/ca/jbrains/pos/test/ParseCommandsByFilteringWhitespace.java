package ca.jbrains.pos.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class ParseCommandsByFilteringWhitespace {
    public ParseCommandsByFilteringWhitespace() {
    }

    public Stream<String> parseCommands(Reader commandSource) {
        return sanitizeCommands(new BufferedReader(commandSource).lines());
    }

    // REFACTOR Extract this into some kind of pluggable filter on Streams of Strings.
    public Stream<String> sanitizeCommands(Stream<String> commandStream) {
        return commandStream
                .map(String::trim)
                .filter((line) -> !line.isEmpty());
    }
}