package io.ngbot.utils.extractors;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;

import lombok.extern.log4j.Log4j2;

@Log4j2
class ContentExtractorTest {

	@Test
	void testJsonContentExtract() {
		String jsonFileResource = "io/ngbot/utils/extractors/sample.json";
		File jsonFile = new File(getClass().getClassLoader().getResource(jsonFileResource).getFile());
		try {
			Object o = JsonPath.read(jsonFile, "meta.view.columns[?(@.position==1)].name");
			logger.info("{}", o.getClass().getName());
			logger.info("{}", o.toString());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
