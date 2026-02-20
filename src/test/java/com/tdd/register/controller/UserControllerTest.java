package com.tdd.register.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.register.controller.request.UserRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @CsvSource({"Jo,12", "Danilo Arantes,1234567"})
    public void should_return_username_length_error(String name, String password) throws Exception {
        var request = new UserRequest(name, password, null);

        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'name')].message",
                        contains("The size should be between 3 and 10")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'password')].message",
                        contains("The size should be between 4 and 6")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'dateOfBirth')].message",
                        contains("The date field cannot be null")));
    }

}
