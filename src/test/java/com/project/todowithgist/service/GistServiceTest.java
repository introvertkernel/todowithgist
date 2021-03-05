package com.project.todowithgist.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.project.todowithgist.OAuthUtils;
import com.project.todowithgist.domain.Project;
import com.project.todowithgist.domain.Todo;
import com.project.todowithgist.domain.User;
import com.project.todowithgist.payload.GistRequest;
import com.project.todowithgist.repository.ProjectRepository;
import com.project.todowithgist.repository.TodoRepository;
import com.project.todowithgist.repository.UserRepository;
import com.project.todowithgist.rest.GistAPI;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GistServiceTest {

	@InjectMocks
	private GistService service;

	@Mock
	private GistAPI gistApi;

//	@Autowired
//	private RestTemplate restTemplate;

	@Mock
	private TodoRepository todoRepository;

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private UserRepository userRepository;

	private OAuth2User principal;

	private List<Todo> todoList;
	private static final String todoListString = "# Test Project\n" + "**Summary:** 1/2 todos completed\n"
			+ "### Pending\n" + "- [ ] Todo2\n" + "### Completed\n" + "- [x] Todo1\n" + "";

	private static GistRequest gistRequest;

	private static final String DESCRIPTION = "Test Project";
	private static Map<String, Map<String, String>> FILES_MAP;
	static {
		FILES_MAP = new HashMap<String, Map<String, String>>();
		FILES_MAP.put("Newfile.md", new HashMap<String, String>() {
			{
				put("content", "Content");
			}
		});
	}

	private static final String JSON_TO_DESERIALIZE = "{\"description\":\"Test Project\",\"files\":{\"Newfile.md\":{\"content\":\"Content\"}}}";

	@BeforeEach
	public void setUp() {
		principal = OAuthUtils.createUser("tester", "tester@example.com");

		User user = new User();
		user.setUserId("tester");
		user.setUserName("myusername");
		user.setAuthToken("uytwruywrihskajdf642");
		Project project = new Project();
		project.setProjectId("P1234");
		project.setProjectName("Test Project");
		project.setUser(user);
		Todo todo1 = new Todo("t1234", project, "Todo1", "C");
		Todo todo2 = new Todo("t1235", project, "Todo2", "P");

		todoList = Arrays.asList(todo1, todo2);

		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(projectRepository.findByProjectId(project.getProjectId())).thenReturn(project);
		Mockito.when(todoRepository.findAllByProjectProjectId(project.getProjectId())).thenReturn(todoList);

	}

	@Test
	public void testBuildGistFile() {
		assertTrue(todoListString.equals(service.buildGistFile("Test Project", todoList)));
	}

	@Test
	public void exportTest() {
//		assertThatNoException().isThrownBy(() -> service.export("tester", "P1234"));
		assertThatExceptionOfType(Exception.class).isThrownBy(() -> service.export("tester", "P1234"));
	}
}
