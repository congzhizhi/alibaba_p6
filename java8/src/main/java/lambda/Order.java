package lambda;

import java.util.Optional;

public class Order {
    String name = "源码之路";
    public String getOrderName(Order order ) {
        // if (order == null) {
        // return null;
        // }
        //
        // return order.name;
        Optional<Order> orderOptional = Optional.ofNullable(order);
        if (!orderOptional.isPresent()) {
            return null;
        }
        return orderOptional.get().name;
    }

    public static void main(String[] args) {
        Order order = new Order();
        System.out.println(order.getOrderName(null));
    }
}