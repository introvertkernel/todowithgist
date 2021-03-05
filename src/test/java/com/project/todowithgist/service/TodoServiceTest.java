package com.project.todowithgist.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.todowithgist.domain.Project;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.TodoPayload;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.TodoRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoServiceTest {

	@InjectMocks
	TodoService service;

	@Mock
	private TodoRepository todoRepository;

	@Mock
	private ProjectRepository projectRepository;

	public static final String PROJECT_ID = "p1234";
	public TodoPayload payload;

	@BeforeEach
	public void setUp() {
		payload = new TodoPayload("t1234", "My task", "C", new Date().from(Instant.now()));
		Mockito.when(projectRepository.findById(PROJECT_ID)).thenReturn(populateProject());
		Mockito.when(projectRepository.findById(PROJECT_ID + 1)).thenReturn(Optional.empty());
	}

	@Test
	public void testAddTodoFindProjectWithNoResponse() throws Exception {
		assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
			service.addTodo(payload, PROJECT_ID + 1);
		});
	}

	@Test
	public void testAddTodoFindProjectWithResponse() throws Exception {
		assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
			service.addTodo(payload, PROJECT_ID);
		});
	}

	@Test
	public void testFetchAll() {
		assertThatNoException().isThrownBy(() -> service.fetchAll(PROJECT_ID));
	}

	@Test
	public void testdDeleteTodo() {
		assertThatNoException().isThrownBy(() -> service.deleteTodo("t1234"));
	}

	private Optional<Project> populateProject() {
		User user = new User("213456", "myusername");
		Project project = new Project("p1234", user, "Projectname", "");
		return Optional.of(project);
	}

}
