package io.spring.lab.warehouse

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

@AutoConfigureTestDatabase
abstract class SpringSpecBase extends Specification {

}
