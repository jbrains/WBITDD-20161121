package ca.jbrains.text;

import java.util.Arrays;
import java.util.List;

public class Text {
    // REFACTOR Isn't there a library function for this?!
    public static List<String> lines(String text) {
        return Arrays.asList(text.split(System.lineSeparator()));
    }
}
