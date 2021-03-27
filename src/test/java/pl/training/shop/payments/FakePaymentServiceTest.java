package pl.training.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FakePaymentServiceTest {

    private static final String PAYMENT_ID = "1";
    private static final FastMoney MONEY = LocalMoney.of(1000);
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(MONEY)
            .build();

    @Mock
    private PaymentIdGenerator paymentIdGenerator;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private Payment payment;

    @BeforeEach
    void setUp(){
        when(paymentIdGenerator.getNext()).thenReturn(PAYMENT_ID);
        when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());
        FakePaymentService fakePaymentService = new FakePaymentService(paymentIdGenerator,paymentRepository,eventPublisher);
        payment = fakePaymentService.process(PAYMENT_REQUEST);
    }

    @DisplayName("czy przypisana jest płatności do naszegi requestu")
    @Test
    void shuoldAssignGeneratedIdToCreatedPayment(){
        assertEquals(PAYMENT_ID,payment.getId());
    }
    @DisplayName("Porównanie kwoty")
    @Test
    void shuoldAssignMoneyFromPaymentRequestToCreatedPayment(){
        assertEquals(MONEY,payment.getMoney());
    }
    @DisplayName("Czy jest stopka czasowa")
    @Test
    void shuoldAssignTimestampToCreatedPayments(){
        assertNotNull(payment.getTimestamp());
    }
    @DisplayName("Czy status jest ustawiony na STARTED")
    @Test
    void shuoldAssignStartedStatusToCreatePayment(){
        Assertions.assertEquals(PaymentStatus.STARTED,payment.getStatus());

    }
    @DisplayName("Czy była wywołana metoda save")
    @Test
    void shuoldSaveCreatedPayment(){
        verify(paymentRepository).save(payment);
    }
}
