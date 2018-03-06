package io.spring.lab.warehouse

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@AutoConfigureTestDatabase
@ActiveProfiles("test")
abstract class SpringSpecBase extends Specification {

}
