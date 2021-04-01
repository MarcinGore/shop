package pl.training.shop.payments;

import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("paymentRepository")
public class JpaPaymentRepository implements PaymentRepository{

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Payment save(Payment payment) {
        entityManager.persist(payment);
        entityManager.flush();
        entityManager.refresh(payment);
        return payment;
    }
}
