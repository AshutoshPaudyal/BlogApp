package com.blogapp.blog.app.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;
    @NotEmpty
    @Size(min = 4,message = "Minimum size of category title is 4")
    private String categoryTitle;

    @NotEmpty
    @Size(min =10,message = "Minimum size of category description is 10")
    private String categoryDescription;
}
