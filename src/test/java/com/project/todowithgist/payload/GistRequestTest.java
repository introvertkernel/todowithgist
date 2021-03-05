package com.project.todowithgist.payload;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@JsonTest
public class GistRequestTest {

	@Autowired
	private JacksonTester<GistRequest> json;

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

	@BeforeAll
	public static void setUp() {
		gistRequest = new GistRequest(DESCRIPTION, FILES_MAP);

	}

	@Test
	public void descriptionSerializes() throws IOException {
		assertThat(this.json.write(gistRequest)).extractingJsonPathStringValue("@.description").isEqualTo(DESCRIPTION);
	}

	@Test
	public void fileMapSerializes() throws IOException {
		assertThat(this.json.write(gistRequest)).extractingJsonPathMapValue("@.files").isEqualTo(FILES_MAP);
	}

	@Test
	public void descriptionDesrializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getDescription()).isEqualTo(DESCRIPTION);
	}

	@Test
	public void filesMapDeserializes() throws IOException {
		assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getFilesMap()).isEqualTo(FILES_MAP);
	}

	@Test
	public void toStringTest() {
		assertEquals("GistRequest [description=Test Project, filesMap={Newfile.md={content=Content}}]",
				gistRequest.toString());
	}

}
