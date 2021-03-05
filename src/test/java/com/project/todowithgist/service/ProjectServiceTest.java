package com.project.todowithgist.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.ProjectPayload;
import com.project.todowithgist.payload.TodoPayload;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.TodoRepository;
import com.project.todowithgist.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectServiceTest {
	@InjectMocks
	private ProjectService service;

	@Mock
	private ProjectRepository projectRespository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TodoRepository todoRepository;

	public ProjectPayload projectPayload;
	private final static String PROJECT_ID = "1234";
	private final static String PROJECT_NAME = "New Project";
	private List<TodoPayload> todoPayload;

	@BeforeEach
	public void setUp() {
		TodoPayload payload1 = new TodoPayload("t1234", "Todo1", "C");
		TodoPayload payload2 = new TodoPayload("t1235", "Todo2", "P");
		todoPayload = new ArrayList<>(java.util.Arrays.asList(payload1, payload2));

		projectPayload = new ProjectPayload(PROJECT_ID, PROJECT_NAME, todoPayload);

		User user = new User();
		user.setUserId("1234567890");
		user.setUserName("myusername");
		Project project = new Project();
		project.setProjectId(projectPayload.getProjectId());
		project.setProjectName(projectPayload.getProjectName());
		project.setUser(user);

		Todo todo1 = new Todo("t1234", project, "Todo1", "C");
		Todo todo2 = new Todo("t1235", project, "Todo2", "P");

		List<Todo> todoList = Arrays.asList(todo1, todo2);

		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(projectRespository.save(project)).thenReturn(project);
		Mockito.when(todoRepository.saveAll(todoList)).thenReturn(todoList);
		Mockito.when(projectRespository.findAllByUserUserId(user.getUserId())).thenReturn(Arrays.asList(project));
	}

	@Test
	public void testAddProjectWithNullPayloadThrowNullPointerException() {
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
			service.addProject(null, "1234567890");
		});
	}

	@Test
	public void testAddProjectWith() {
		assertThatNoException().isThrownBy(() -> {
			service.addProject(projectPayload, "1234567890");
		});
	}

	@Test
	public void testFetchAllByUserId() {
		assertThatNoException().isThrownBy(() -> service.fetchAll("1234567890"));
	}

	@Test
	public void testDeleteById() {
		assertThatNoException().isThrownBy(() -> service.deleteProject(PROJECT_ID));
	}

	private List<Todo> populateTodoList() {
		User user = new User("213456", "myusername");
		Project project = new Project("p1234", user, "Projectname", "");
		Todo payload1 = new Todo("t1234", project, "Todo1", "C");
		Todo payload2 = new Todo("t1235", project, "Todo2", "P");
		return new ArrayList<Todo>(java.util.Arrays.asList(payload1, payload2));
	}
}
