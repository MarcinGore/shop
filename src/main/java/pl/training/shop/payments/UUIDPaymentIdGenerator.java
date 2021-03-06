package pl.training.shop.payments;

import org.springframework.stereotype.Component;

import java.util.UUID;

@IdGenerator("uuid")
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {
    @Override
    public String getNext(){
        return UUID.randomUUID().toString();
    }
}
