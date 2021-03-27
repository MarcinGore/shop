package pl.training.shop.payments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IncrementalPaymentIdGeneratorTest {

    private static final String ID_FORMAT = "\\d{10}";

    private final IncrementalPaymentIdGenerator paymentIdGenerator= new IncrementalPaymentIdGenerator();

    @DisplayName("Walidacja generatora")
    @Test
    void shouldGenerateValidId(){
        String id=paymentIdGenerator.getNext();
        Assertions.assertTrue(id.matches(ID_FORMAT));
    }

    @DisplayName("kolejny przez dodanie nowej wartości")
    @Test
    void shuoldGenerateIdByIncrementingValueOfPreviousOne(){
        long firstId = Long.parseLong(paymentIdGenerator.getNext());
        long secondtId = Long.parseLong(paymentIdGenerator.getNext());
        Assertions.assertEquals(firstId+1,secondtId);
    }
}
