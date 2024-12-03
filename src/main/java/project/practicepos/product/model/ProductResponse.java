package project.practicepos.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String categoryId;
    private String categoryName;
    private String quantity;
    private String price;
    private String stock;
    private String onOrder;
}
