package edu.xmu.test.javase.misc;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class BrowseFileTest {
	@Test
	public void browseFile() throws IOException {
		Path path = new File("src/main/resources/log4j.xml").toPath();
		if (System.getProperty("os.name").startsWith("Windows") && StringUtils.isEmpty(System.getProperty("surefire.test.class.path"))) {
			Desktop.getDesktop().browse(path.toUri());
		} else {
			Files.delete(path);
		}
	}
}
