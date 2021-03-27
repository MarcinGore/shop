package pl.training.shop.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class LocalPaymentRepositoryTest {

    private static final String PAYMENT_ID="1";
    private static final Payment PAYMENT = Payment.builder()
            .id(PAYMENT_ID)
            .build();
    private final LocalPaymentRepository paymentRepository = new LocalPaymentRepository();

    @Mock
    private Map<String,Payment> payments;

    @BeforeEach
    void setUp(){
        paymentRepository.setPayments(payments);
    }

    @DisplayName("Czy dodawana jest płatność pod kluczem payment ID")
    @Test
    void shouldAddPaymentToLocalRepositoryUnderPaymentId(){
        paymentRepository.save(PAYMENT);
        Mockito.verify(payments).put(PAYMENT_ID,PAYMENT);
    }
}
