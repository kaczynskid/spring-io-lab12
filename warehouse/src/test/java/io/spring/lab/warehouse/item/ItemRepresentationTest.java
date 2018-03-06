package io.spring.lab.warehouse.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
@ActiveProfiles("test")
public class ItemRepresentationTest {

    @Autowired JacksonTester<ItemRepresentation> json;

    @Test
    public void shouldSerializeItemRepresentation() throws Exception {
        ItemRepresentation item = new ItemRepresentation(1L, "A", 100, BigDecimal.valueOf(40.0));

        JsonContent<ItemRepresentation> result = json.write(item);

        assertThat(result).extractingJsonPathNumberValue("@.id")
                .isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("@.name")
                .isEqualTo("A");
        assertThat(result).extractingJsonPathNumberValue("@.count")
                .isEqualTo(100);
        assertThat(result).extractingJsonPathNumberValue("@.price")
                .isEqualTo(40.0);
    }

    @Test
    public void shouldDeserializeItemRepresentation() throws Exception {
        String data = "{\"id\":1,\"name\":\"A\",\"count\":100,\"price\":40.0}";

        ItemRepresentation result = json.parse(data).getObject();

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("A");
        assertThat(result.getCount()).isEqualTo(100);
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(40.0));
    }
}
