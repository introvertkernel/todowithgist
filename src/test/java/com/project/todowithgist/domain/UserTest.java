package com.project.todowithgist.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

	private User user1;
	private static final String toString = "User [userId=tester1, userName=Real tester, authToken=kjuita6dfba8]";

	@BeforeEach
	public void setUp() {
		user1 = new User("tester1", "Real tester", "kjuita6dfba8");
	}

	@Test
	public void testToString() {
		System.out.println(user1.toString());
		assertThat(toString.equals(user1));
	}
}
