package ca.jbrains.pos.test;

import java.io.Reader;
import java.util.stream.Stream;

public interface ParseCommands {
    Stream<String> parseCommands(Reader commandSource);
}
