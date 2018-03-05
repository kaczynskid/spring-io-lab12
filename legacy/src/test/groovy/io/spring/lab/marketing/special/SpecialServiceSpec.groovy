package io.spring.lab.marketing.special

import io.spring.lab.marketing.special.calculate.SpecialCalculation
import io.spring.lab.marketing.special.select.BestSpecialSelector
import io.spring.lab.marketing.special.select.SpecialSelector
import io.spring.lab.math.MathProperties
import spock.lang.Specification
import spock.lang.Unroll

import static io.spring.lab.marketing.TestDataConfiguration.specialsTestData

class SpecialServiceSpec extends Specification {

    MathProperties math = new MathProperties()
    SpecialSelector selector = new BestSpecialSelector(math)
    SpecialRepository repository = new StubSpecialRepository()

    SpecialService specials = new SpecialService(repository, selector, math)

    @Unroll("special price of #unitCount items should be #totalPrice")
    def "calculates special price"() {
        given:
            specialsTestData(repository)
        when:
            SpecialCalculation calculation = specials.calculateFor(itemId, unitPrice, unitCount)
        then:
            calculation.specialId != null
            calculation.totalPrice == totalPrice
        where:
            itemId | unitPrice | unitCount | totalPrice
            1      | 40.0      | 3         | 70.0
            2      | 10.0      | 2         | 15.0
            3      | 30.0      | 4         | 60.0
            4      | 25.0      | 2         | 40.0
    }
}
