package io.ngbot.utils;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
class MimeTypeDetectionTest {

	private Tika tika;
	
//	@BeforeAll
//	static void init() {
//		tika = new Tika();
//	}
	
	@Test
	void testDetectMimeType() {
		tika = new Tika();
		final String[] fileResources =  new String[] {
				"io/ngbot/utils/detection/ext/sample.xml",
				"io/ngbot/utils/detection/ext/sample.html",
				"io/ngbot/utils/detection/ext/sample.json",
				"io/ngbot/utils/detection/ext/sample.yaml",
				"io/ngbot/utils/detection/ext/sample.yml",
		};
		for (String fileResource : fileResources) {
			File file = new File(getClass().getClassLoader().getResource(fileResource).getFile());
			logger.info("Trying to detect mime type for {}", file.getAbsolutePath());
			try {
				long start = System.currentTimeMillis();
				String mimeType = tika.detect(file);
				logger.info("Detected mime type {}", mimeType);
				logger.info("Total time taken to detect mimetype - {} ms", (System.currentTimeMillis() - start));
			} catch (IOException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		}
	}
	
	@Test
	void testDetectMimeTypeNoExt() {
		tika = new Tika();
		final String[] fileResources =  new String[] {
				"io/ngbot/utils/detection/sample_xml",
				"io/ngbot/utils/detection/sample_html",
				"io/ngbot/utils/detection/sample_json",
				"io/ngbot/utils/detection/sample_yaml",
				"io/ngbot/utils/detection/sample_yml",
		};
		for (String fileResource : fileResources) {
			File file = new File(getClass().getClassLoader().getResource(fileResource).getFile());
			logger.info("Trying to detect mime type for {}", file.getAbsolutePath());
			try {
				long start = System.currentTimeMillis();
				String mimeType = tika.detect(file);
				logger.info("Detected mime type {}", mimeType);
				logger.info("Total time taken to detect mimetype - {} ms", (System.currentTimeMillis() - start));
			} catch (IOException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		}
	}
}
