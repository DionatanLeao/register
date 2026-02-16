package com.tdd.register.service;

import com.tdd.register.domain.User;
import com.tdd.register.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Test
    public void should_return_error_when_age_is_greater_than_sixty() {
        User user = new User(null, "Danilo", "1234", LocalDate.of(1960, 4, 10));

        var exception = Assertions.assertThrows(RuntimeException.class, () -> userService.register(user));

        Mockito.verifyNoInteractions(userRepository);
        Mockito.verify(userRepository, Mockito.never()).save(user);
        Assertions.assertEquals("Age not allowed", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {2010, 1960})
    public void should_return_error_when_age_is_invalid(Integer year) {
        User user = new User(null, "Danilo", "1234", LocalDate.of(year, 4, 10));

        var exception = Assertions.assertThrows(RuntimeException.class, () -> userService.register(user));

        Mockito.verifyNoInteractions(userRepository);
        Mockito.verify(userRepository, Mockito.never()).save(user);
        Assertions.assertEquals("Age not allowed", exception.getMessage());
    }

}
