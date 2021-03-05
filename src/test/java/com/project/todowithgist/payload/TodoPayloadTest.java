package com.project.todowithgist.payload;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@JsonTest
public class TodoPayloadTest {

	@Autowired
	private JacksonTester<TodoPayload> json;

	private static TodoPayload payload;

	private static final String TODO_ID = "1234";
	private static final String TODO_DESC = "MY TASK";
	private static final String TODO_STATUS = "P";

	private static final String JSON_TO_DESERIALIZE = "{\"todoId\":\"1234\",\"todoDesc\":\"MY TASK\",\"todoStatus\":\"P\"}";

	@BeforeAll
	private static void setUp() {
		payload = new TodoPayload(TODO_ID, TODO_DESC, TODO_STATUS, new Date().from(Instant.now()));

	}

	@Test
	public void todoIdSerializes() throws IOException {
		assertThat(this.json.write(payload)).extractingJsonPathStringValue("@.todoId").isEqualTo(TODO_ID);
	}

	@Test
	public void todoDescSerializes() throws IOException {
		assertThat(this.json.write(payload)).extractingJsonPathStringValue("@.todoDesc").isEqualTo(TODO_DESC);
	}

	@Test
	public void todoStatusSerializes() throws IOException {
		assertThat(this.json.write(payload)).extractingJsonPathStringValue("@.todoStatus").isEqualTo(TODO_STATUS);
	}

	@Test
	public void todoIdDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getTodoId()).isEqualTo(TODO_ID);
	}

	@Test
	public void todoDescDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getTodoDesc()).isEqualTo(TODO_DESC);
	}

	@Test
	public void todoStatusDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getTodoStatus()).isEqualTo(TODO_STATUS);
	}

	@Test
	public void toStringTest() {
		assertEquals("TodoPayload [todoId=1234, todoDesc=MY TASK, todoStatus=P]", payload.toString());
	}
}
