package edu.xmu.test.javase;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by davywalker on 16/10/24.
 * 生成简化的包名. 例如:
 * edu.xmu.test.javase.AbbreviatePackageTest ->
 * e.x.t.javase.AbbreviatePackageTest
 */
public class AbbreviatePackageTest {
    @Test
    public void abbreviateTest() {
        String str = "edu.xmu.test.javase.AbbreviatePackageTest";
        List<String> strings = Splitter.on('.').splitToList(str);

    }
}
