package pl.training.shop.payments;

import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class PaymentConsoleLogger {

    private static final String LOG_FORMAT = "Platnosci %s zainicjowana";

    @AfterReturning(value = "@annotation(LogPayments)", returning = "payment")
    public void log(Payment payment){
        log.info(createLogEntry(payment));
    }


    private String createLogEntry(Payment payment){
        return String.format(LOG_FORMAT,payment.getMoney());
    }

}
