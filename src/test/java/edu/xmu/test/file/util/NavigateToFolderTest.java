package edu.xmu.test.file.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 根据操作系统(windows/mac)自动打开文件所在目录
 *
 */
public class NavigateToFolderTest {
	@Test
	public void navigateToFolder() throws InterruptedException, IOException {
		String osName = System.getProperty("os.name");
		System.out.println(osName);
		if (StringUtils.isNotBlank(osName) && osName.startsWith("Windows")) {
			String path = "c:/";
			// 打开explorer目录
			java.awt.Desktop.getDesktop().open(new File(path));
		} else if (StringUtils.isNotBlank(osName) && osName.startsWith("Mac")) {
			String path = "/Users/davyjones/files/tmp";
			// 打开finder目录
			Runtime.getRuntime().exec("/usr/bin/open " + path).waitFor();
		} else {
			System.out.println("Unsupported os: " + osName);
		}
	}
}
