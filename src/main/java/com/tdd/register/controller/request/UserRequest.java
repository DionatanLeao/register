package com.tdd.register.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    @Size(min = 3, max = 10, message = "The size should be between 3 and 10")
    private String name;
    @NotBlank
    @Size(min = 4, max = 6, message = "The size should be between 4 and 6")
    private String password;
    @NotNull(message = "The date field cannot be null")
    private LocalDate dateOfBirth;
}
