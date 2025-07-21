package com.blogapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(min =10, message = "Add minimum 10 characters")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 8, message = "This section size must be greater than 8 character")
    private String categoryDescription;
}
