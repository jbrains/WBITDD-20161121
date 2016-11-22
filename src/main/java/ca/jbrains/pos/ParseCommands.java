package ca.jbrains.pos;

import java.io.Reader;
import java.util.stream.Stream;

public interface ParseCommands {
    Stream<String> parseCommands(Reader commandSource);
}
