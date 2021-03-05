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

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@JsonTest
public class ResponseHeaderTest {

	@Autowired
	private JacksonTester<ResponseHeader> json;

	private static ResponseHeader header;

	private static final String RESPONSE_CODE = "SUC";
	private static final String ERROR_CODE = "99";
	private static final String ERROR_MESSAGE = "Something went wrong";

	private static final String JSON_TO_DESERIALIZE = "{\"responseCode\":\"SUC\",\"errorCode\":\"99\",\"errorMessage\":\"Something went wrong\"}";

	@BeforeAll
	public static void setUp() {
		header = new ResponseHeader(RESPONSE_CODE, ERROR_CODE, ERROR_MESSAGE);
	}

	@Test
	public void responseCodeSerializes() throws IOException {
		System.out.println(header.toString());
		System.out.println(new ObjectMapper().writeValueAsString(header));
		assertThat(this.json.write(header)).extractingJsonPathStringValue("@.responseCode").isEqualTo(RESPONSE_CODE);
	}

	@Test
	public void errorCodeSerializes() throws IOException {
		assertThat(this.json.write(header)).extractingJsonPathStringValue("@.errorCode").isEqualTo(ERROR_CODE);
	}

	@Test
	public void errorMessageSerializes() throws IOException {
		assertThat(this.json.write(header)).extractingJsonPathStringValue("@.errorMessage").isEqualTo(ERROR_MESSAGE);
	}

	@Test
	public void responseCodeDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getResponseCode()).isEqualTo(RESPONSE_CODE);
	}

	@Test
	public void errorCodeDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getErrorCode()).isEqualTo(ERROR_CODE);
	}

	@Test
	public void errorMessageDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getErrorMessage()).isEqualTo(ERROR_MESSAGE);
	}

	@Test
	public void toStringTest() {
		assertEquals("ResponseHeader [responseCode=SUC, errorCode=99, errorMessage=Something went wrong]",
				header.toString());
	}
}
