package edu.xmu.test.file.util;

import java.io.File;
import java.util.Calendar;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

public class FilenameUtilTest {

    @Test
    public void getFileName() {
        File file = new File("src/test/resources/input.csv");
        System.out.println(file.getAbsolutePath());
        System.out.println(insertIdIntoFileName(file.getAbsolutePath(),
                                                Calendar.getInstance().getTimeInMillis() + "_transform_errors"));
    }

    String insertIdIntoFileName(String origFilename, String id) {
        return FilenameUtils.getFullPath(origFilename).concat(FilenameUtils.getBaseName(origFilename)).concat("_").concat(id).concat(".").concat(FilenameUtils.getExtension(origFilename));
    }
}
