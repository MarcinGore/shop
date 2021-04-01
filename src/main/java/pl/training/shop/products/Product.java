package pl.training.shop.products;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.javamoney.moneta.FastMoney;

import javax.persistence.*;

@NamedQuery(name = Product.SELECT_PRODUCTS, query="select p from Product p")
@Table(name = "products",indexes = @Index(name="product_name",columnList = "type"))
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Product {

    public static final String SELECT_PRODUCTS = "selectProducts";

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String description;
    @Columns(columns = {
            @Column(name="currency", length=3),
            @Column(name="value",length = 15)
    })
    private FastMoney price;
    @Enumerated(EnumType.STRING)
    private ProductType type;
}
