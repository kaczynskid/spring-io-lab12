package io.spring.lab.store.basket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;

import io.spring.lab.store.SpringTestBase;
import io.spring.lab.store.basket.item.BasketItem;
import io.spring.lab.store.basket.item.BasketItemService;
import io.spring.lab.store.item.ItemRepresentation;
import io.spring.lab.store.item.ItemsClient;

@SpringBootTest(properties = {
        "ribbon.eureka.enabled=false",
        "marketing.ribbon.listOfServers=127.0.0.1:9989"
})
@AutoConfigureStubRunner(
        workOffline = true,
        ids = "io.spring.lab:marketing:+:stubs:9989"
)
public class BasketServiceTest extends SpringTestBase {

    @MockBean ItemsClient items;

    @Autowired BasketService baskets;
    @Autowired BasketItemService basketItems;

    private long itemId = 1L;

    @Before
    public void setUp() throws Exception {
        when(items.findOne(itemId))
                .thenReturn(new ItemRepresentation("A", BigDecimal.valueOf(40.0)));
    }

    @Test
    public void shouldUpdateBasketWithRegularPriceItem() throws Exception {
        Basket basket = baskets.create();
        int count = 2;

        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), itemId, count);

        assertThat(diff.getCountDiff()).isEqualTo(2);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(BigDecimal.valueOf(80.0));

        BasketItem basketItem = basketItems.findOneItem(basket.getId(), itemId);
        assertThat(basketItem.getName()).isEqualTo("A");
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(80.0));
        assertThat(basketItem.getSpecialId()).isNull();
    }

    @Test
    public void shouldUpdateBasketWithSpecialPriceItem() throws Exception {
        Basket basket = baskets.create();
        int count = 5;

        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), itemId, count);

        assertThat(diff.getCountDiff()).isEqualTo(5);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(BigDecimal.valueOf(150.0));

        BasketItem basketItem = basketItems.findOneItem(basket.getId(), itemId);
        assertThat(basketItem.getName()).isEqualTo("A");
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(150.0));
        assertThat(basketItem.getSpecialId()).isEqualTo("abcdefghijklmnopqrstuw0123456789");
    }
}
