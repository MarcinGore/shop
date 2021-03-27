package pl.training.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.training.shop.common.PagedResult;
import pl.training.shop.common.retry.Retry;
import pl.training.shop.orders.Order;
import pl.training.shop.orders.OrderService;
import pl.training.shop.payments.Payment;
import pl.training.shop.payments.PaymentRequest;
import pl.training.shop.payments.PaymentService;
import pl.training.shop.products.Product;
import pl.training.shop.products.ProductService;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ProductService productService;

    public Product addProduct(Product product){
        return productService.add(product);
    }

    public PagedResult<Product> getProducts(int pageNumber,int pageSize){
        return productService.getAll(pageNumber,pageSize);
    }

    public Order placeOrder(Order order){
        return orderService.add(order);
    }

    @Retry(numberRetry = 10)
    public Payment payForOrder(Long orderId) throws RuntimeException{
        var order = orderService.getBy(orderId);
        var paymenRequest = PaymentRequest.builder()
                .money(order.getTotalPrice())
                .build();
        var payment = paymentService.process(paymenRequest);
        order.setPayment(payment);
        orderService.update(order);
        throw new RuntimeException();
        //return payment;
    }
}


