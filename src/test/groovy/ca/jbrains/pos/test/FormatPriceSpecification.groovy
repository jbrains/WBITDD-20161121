package ca.jbrains.pos.test

import ca.jbrains.pos.EnglishLanguageMessageFormat
import ca.jbrains.pos.Price
import spock.lang.Specification
import spock.lang.Unroll

class FormatPriceSpecification extends Specification {
    @Unroll
    def "format price €#price.euro()"() {
        expect:
        new EnglishLanguageMessageFormat().format(price) == text

        where:
        price                 || text
        Price.cents(168)      || "EUR 1.68"
        Price.cents(170)      || "EUR 1.70"
        Price.cents(200)      || "EUR 2.00"
        Price.cents(89)       || "EUR 0.89"
        Price.cents(2)        || "EUR 0.02"
        Price.cents(0)        || "EUR 0.00"
        Price.cents(-823)     || "EUR -8.23"
        Price.cents(23874623) || "EUR 238746.23"
    }
}