package ca.jbrains.pos.test;

public class EnglishLanguageMessageFormat {
    public String format(Price price) {
        return String.format("EUR %.2f", price.euro());
    }
}
