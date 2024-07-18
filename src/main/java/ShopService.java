import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {

            try{

                Optional<Product> productToOrder = productRepo.getProductById(productId);
                products.add(productToOrder.get());

            } catch (Exception e){

                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!" + e.getMessage());
                return null;
            }
        }
        Order newOrder = new Order(UUID.randomUUID().toString(), products,OrderStatus.PROCESSING, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepo.getOrders();

        return orders.stream().filter(o -> o.orderStatus().equals(status))
                              .toList();
    }

    public Order updateOrder(String orderId) {
        Order order = orderRepo.getOrderById(orderId);
        if (order != null) {
            if (order.orderStatus().equals(OrderStatus.PROCESSING)) {
                return order.withOrderStatus(OrderStatus.IN_DELIVERY);
            }
            if (order.orderStatus().equals(OrderStatus.IN_DELIVERY)) {
                return order.withOrderStatus(OrderStatus.COMPLETED);
            }
        }

        return order;
    }

}
