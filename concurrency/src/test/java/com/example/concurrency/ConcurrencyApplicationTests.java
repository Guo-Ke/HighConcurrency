package com.example.concurrency;

import com.example.concurrency.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ConcurrencyApplicationTests {

	@Test
	void contextLoads() {
		User user = new User();
		assertNull(user.usnm()); // JUnit assertion
		assertThat(user.usnm()).isNull(); // Fest assertion
	}

}
