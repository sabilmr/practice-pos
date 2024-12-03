package project.practicepos.category.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String id;

    @NotEmpty(message = "name is not empty")
    private String name;

    @NotEmpty(message = "description is not empty")
    private String description;
}
