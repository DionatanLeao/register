package com.tdd.register;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class RegisterApplicationTests {
	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;

	@Test
	void should_register_user_successfully() {
		User userBeforeSave = new User("Danilo", "1234", LocalDate.of(1992, 10, 6));
		User userAfterSave = userBeforeSave;
		userAfterSave.setId("1");
		Mockito.when(userRepository.save(user)).thenReturn(userAfterSave);

		var user = userService.register(userBeforeSave);

		Mockito.verify(userRepository).save(userBeforeSave);
		Assertions.assertEquals(userAfterSave, user);
	}

}
