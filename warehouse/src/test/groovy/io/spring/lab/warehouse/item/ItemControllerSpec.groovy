package io.spring.lab.warehouse.item

import com.fasterxml.jackson.databind.ObjectMapper
import io.spring.lab.warehouse.SpringSpecBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerSpec extends SpringSpecBase {

    @Autowired MockMvc mvc

    @Autowired ObjectMapper json

    def "Should get all items"() {
        when:
            def resp = mvc.perform(get('/items'))
        then:
            resp.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$.[0].name').value('A'))
                .andExpect(jsonPath('$.[0].price').value('40.0'))
    }

    def "Should create item"() {
        when:
            def resp = mvc.perform(post('/items').contentType(APPLICATION_JSON_UTF8)
                    .content(json.writeValueAsString([name: 'test', count: 5, price: 13.5])))
        then:
            resp.andExpect(status().isCreated())
                    .andExpect(header().string('Location', 'http://localhost/items/5'));
    }

    def "Should find item"() {
        when:
            def resp = mvc.perform(get('/items/2'))
        then:
            resp.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$.id').value(2L))
                .andExpect(jsonPath('$.name').value('B'))
                .andExpect(jsonPath('$.count').value(100))
                .andExpect(jsonPath('$.price').value(10))
    }

    def "Should update item"() {
        when:
            def resp = mvc.perform(put('/items/3').contentType(APPLICATION_JSON_UTF8)
                    .content(json.writeValueAsString([name: 'item', price: 27.3])))
        then:
            resp.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$.id').value(3L))
                .andExpect(jsonPath('$.name').value('item'))
                .andExpect(jsonPath('$.count').value(100))
                .andExpect(jsonPath('$.price').value(27.3))
    }

    def "Should update item stock"() {
        when:
            def resp = mvc.perform(put('/items/4/stock').contentType(APPLICATION_JSON_UTF8)
                    .content(json.writeValueAsString([countDiff: -27])))
        then:
            resp.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$.id').value(4L))
                .andExpect(jsonPath('$.name').value('D'))
                .andExpect(jsonPath('$.count').value(73))
                .andExpect(jsonPath('$.price').value(25))
    }
}
