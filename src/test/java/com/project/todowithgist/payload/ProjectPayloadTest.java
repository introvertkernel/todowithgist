package com.project.todowithgist.payload;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@JsonTest
public class ProjectPayloadTest {

	@Autowired
	private JacksonTester<ProjectPayload> json;

	private static ProjectPayload payload;

	private final static String PROJECT_ID = "1234";
	private final static String PROJECT_NAME = "New Project";
	private static List<TodoPayload> todoPayload;

	private static final String JSON_TO_DESERIALIZE = "{\"projectId\":\"1234\",\"projectName\":\"New Project\",\"todoPayload\":[{\"todoId\":\"t1234\",\"todoDesc\":\"Todo1\",\"todoStatus\":\"C\"},{\"todoId\":\"t1235\",\"todoDesc\":\"Todo2\",\"todoStatus\":\"P\"}]}";

	@BeforeAll
	public static void setUp() {
		TodoPayload payload1 = new TodoPayload("t1234", "Todo1", "C", new Date().from(Instant.now()));
		TodoPayload payload2 = new TodoPayload("t1235", "Todo2", "P", new Date().from(Instant.now()));
		todoPayload = new ArrayList<>(java.util.Arrays.asList(payload1, payload2));

		payload = new ProjectPayload(PROJECT_ID, PROJECT_NAME, todoPayload);
	}

	@Test
	public void projectIdSerializes() throws IOException {
		assertThat(this.json.write(payload)).extractingJsonPathStringValue("@.projectId").isEqualTo(PROJECT_ID);
	}

	@Test
	public void projectNameSerializes() throws IOException {
		assertThat(this.json.write(payload)).extractingJsonPathStringValue("@.projectName").isEqualTo(PROJECT_NAME);
	}

	@Test
	public void projectIdDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getProjectId()).isEqualTo(PROJECT_ID);
	}

	@Test
	public void projectNameDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getProjectName()).isEqualTo(PROJECT_NAME);
	}

	@Test
	public void toStringTest() {
		assertEquals(
				"ProjectPayload [projectId=1234, projectName=New Project, todoPayload=[TodoPayload [todoId=t1234, todoDesc=Todo1, todoStatus=C], TodoPayload [todoId=t1235, todoDesc=Todo2, todoStatus=P]]]",
				payload.toString());
	}

}
