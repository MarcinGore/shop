package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Aspect
@Component
@Log
@RequiredArgsConstructor
public class PaymentConsoleLogger {

    private static final String MESSAGE_KEY="paymentInfo";

    private final MessageSource messageSource;

    @Before(value = "@annotation(LogPayments) && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest){
        log.info("New payment request :" +paymentRequest);
    }

    @After("@annotation(LogPayments)")
    public void afterPayment(){
        log.info("After payment");
    }

    @AfterThrowing(value = "@annotation(LogPayments)",throwing = "exception")
    public void onException(RuntimeException exception){
        log.info("Payment exception "+exception.getMessage());
    }

    @AfterReturning(value = "@annotation(LogPayments)", returning = "payment")
    public void log(Payment payment){
        log.info(createLogEntry(payment));
    }


    private String createLogEntry(Payment payment){

        return messageSource.getMessage(MESSAGE_KEY, new String[]{payment.getMoney().toString()}, Locale.getDefault());
    }

}
