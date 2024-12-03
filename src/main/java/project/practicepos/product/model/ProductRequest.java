package project.practicepos.product.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String id;

    @NotEmpty(message = "name is not empty")
    private String name;

    @NotEmpty(message = "category is not empty")
    private String categoryId;

    @NotEmpty(message = "quantity is not empty")
    private String quantity;

    @NotEmpty(message = "price is not empty")
    private String price;

    @NotEmpty(message = "stock is not empty")
    private String stock;

    @NotEmpty(message = "on order is not empty")
    private String onOrder;
}
