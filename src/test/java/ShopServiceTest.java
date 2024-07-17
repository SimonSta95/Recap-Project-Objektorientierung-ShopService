import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_throwError() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2", "3");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void testGetOrdersByStatus() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        //THEN
        assertEquals(1, actual.size());

    }

    @Test
    void testOrderUpdate() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order order = shopService.addOrder(productsIds);

        //WHEN
        Order actual = shopService.updateOrder(order.id());

        //THEN
        assertEquals(OrderStatus.IN_DELIVERY, actual.orderStatus());
    }
}
