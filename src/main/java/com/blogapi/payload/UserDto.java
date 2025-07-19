package com.blogapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Name should contain at least 4 character !!")
    private String name;

    @Email(message = "Invalid mail Id !!")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 10, message = "must be between 4 - 10 characters")
    private String password;

    @NotEmpty(message = "this section cannot be empty !!")
    private String about;

}
