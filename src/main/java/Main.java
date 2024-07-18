import java.util.List;


public class Main {
    public static void main(String[] args) {
        ProductRepo shopRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();

        ShopService shopService = new ShopService(shopRepo,orderRepo);
        List<String> productsIds = List.of("1");

        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);


    }
}
