package com.project.todowithgist.payload;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@JsonTest
public class GistResponseTest {

	@Autowired
	private JacksonTester<GistResponse> json;

	private static GistResponse response;

	private static final String ID = "12345678";
	private static final String CREATED_AT = "1970-01-01 00:00:00";

	private static final String JSON_TO_DESERIALIZE = "{\"id\":\"12345678\",\"created_at\":\"1970-01-01 00:00:00\"}";

	@BeforeAll
	public static void setUp() {
		response = new GistResponse();
		response.setId(ID);
		response.setCreated_at(CREATED_AT);
	}

	@Test
	public void idSerializes() throws IOException {
		assertThat(this.json.write(response)).extractingJsonPathStringValue("@.id").isEqualTo(ID);
	}

	@Test
	public void createAtSerializes() throws IOException {
		assertThat(this.json.write(response)).extractingJsonPathStringValue("@.created_at").isEqualTo(CREATED_AT);
	}

	@Test
	public void idDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getId()).isEqualTo(ID);
	}

	@Test
	public void createAtDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getCreated_at()).isEqualTo(CREATED_AT);
	}

	@Test
	public void toStringTest() {
		assertEquals("GistResponse [id=12345678, created_at=1970-01-01 00:00:00]", response.toString());
	}
}
