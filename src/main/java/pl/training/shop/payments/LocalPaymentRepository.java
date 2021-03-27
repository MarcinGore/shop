package pl.training.shop.payments;

import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Repository
public class LocalPaymentRepository implements PaymentRepository{


    @Setter
    private Map<String, Payment> payments = new HashMap<>();

    @Override
    public Payment save(Payment payment) {
        payments.put(payment.getId(),payment);
        return payment;
    }
}
