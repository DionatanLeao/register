package com.tdd.register.service;

import com.tdd.register.domain.User;
import com.tdd.register.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void should_register_user_successfully() {
        User userBeforeSave = new User(null, "Danilo", "1234", LocalDate.of(1992, 10, 6));
        User userAfterSave = userBeforeSave;
        userAfterSave.setId("1");
        Mockito.when(userRepository.save(userBeforeSave)).thenReturn(userAfterSave);

        var user = userService.register(userBeforeSave);

        Mockito.verify(userRepository).save(userBeforeSave);
        Assertions.assertEquals(userAfterSave, user);
    }

    @Test
    public void should_return_error_when_age_is_less_than_eighteen() {
        User user = new User(null, "Danilo", "1234", LocalDate.of(2010, 4, 10));

        var exception = Assertions.assertThrows(RuntimeException.class, () -> userService.register(user));

        Mockito.verifyNoInteractions(userRepository);
        Mockito.verify(userRepository, Mockito.never()).save(user);
        Assertions.assertEquals("Age not allowed", exception.getMessage());

    }

}
