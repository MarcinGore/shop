package pl.training.shop.products;

import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.training.shop.common.PagedResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("productRepository")
public class JpaProductRepository implements ProductRepository{

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(Product product) {
        entityManager.persist(product);
        entityManager.flush();
        entityManager.refresh(product);
        return product;
    }

    @Override
    public PagedResult<Product> findAll(int pageNumber, int pageSize) {
        List<Product> products= entityManager.createNamedQuery(Product.SELECT_PRODUCTS,Product.class)
                .setFirstResult(pageNumber*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return new PagedResult<>(products,pageNumber,-1);
    }
}
