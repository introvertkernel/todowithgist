package com.project.todowithgist.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectTest {

	private Project p1;
	private Project p2;

	@BeforeEach
	public void setUp() {
		User user = new User();
		user.setUserId("1234567890");
		user.setUserName("myusername");
		p1 = new Project();
		p1.setProjectId("P1234");
		p1.setProjectName("TestProject");
		p1.setGistID("87678345");
		p1.setUser(user);

		p2 = new Project();
		p2.setProjectId("P1235");
		p2.setProjectName("TestProject");
		p2.setUser(user);
	}

	@Test
	public void testEquals() {
		assertThat(!p1.equals(p2));
		assertThat(!p1.equals(new Project("P1234")));
	}

	@Test
	public void testEqualsWithSameObject() {
		assertThat(p1.equals(p1));
	}

	@Test
	public void testHashCode() {
		assertThat(p1.hashCode() != p2.hashCode());
	}
}
