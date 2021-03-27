package pl.training.shop.payments;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;

@Log
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service("paymentService")
public class FakePaymentService implements PaymentService {


    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public FakePaymentService(@IdGenerator("incremental") PaymentIdGenerator paymentIdGenerator,
                              PaymentRepository paymentRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.paymentIdGenerator = paymentIdGenerator;
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }


    @LogPayments
    @Override
    public Payment process(PaymentRequest paymentRequest){
        var payment = Payment.builder()
                .id(paymentIdGenerator.getNext())
                .money(paymentRequest.getMoney())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();
        eventPublisher.publishEvent(new PaymentStatusChangedEvent(this,payment));
       //throw new RuntimeException();
         return paymentRepository.save(payment);
    }

    @PostConstruct
    public void init(){
        log.info("Payment Service initialized");
    }

    @PreDestroy
    public void destroy(){
        log.info("PaymentService is going down");
    }

}
