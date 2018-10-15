package edu.xmu.test.framework.spring.messagesource;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by davywalker on 16/9/29.
 *
 */
public class MessageSourceTest {
    @Test
    public void resourceBundleTest() {
        // 1. ResourceBundle.getBundle会从classpath下找对应的${name}_{locale}.class,${name}_${locale}.properties文件
        ResourceBundle myResources =
                ResourceBundle.getBundle("edu.xmu.resourceBundleTest.framework.spring.messagesource.MyResources", Locale.CHINA);
        String okKey = myResources.getString("OkKey");
        System.out.println(okKey);
        myResources = ResourceBundle.getBundle("i18n.MyResources", Locale.US);
        okKey = myResources.getString("OkKey");
        System.out.println(okKey);
    }

    /**
     * MessageSource有两个实现:
     * <ol>
     * <li> MessageSource->ResourceBundleMessageSource</li>
     * <li>MessageSource->StaticMessageSource</li>
     * </ol>
     * MessageSource比ResourceBundle多的功能: <br/>
     * 1. 支持传入参数
     * 2. 支持多个basename
     * 3. 支持嵌套
     * 4. 与ResourceBundle使用相同的fail-fault匹配规则
     */
    @Test
    public void messageSourceTest() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(new String[]{"i18n/common"});
        //resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        Locale.setDefault(Locale.US);
        // 匹配顺序: ${basename}_${locale}.properties-->${basename}_${default_locale}.properties-->${basename}.properties
        String message = resourceBundleMessageSource.getMessage("btn.ok", new Object[]{"davywalker"}, Locale.US);
        System.out.println(message);
        message = resourceBundleMessageSource.getMessage("btn.ok", new Object[]{"昆仑"}, Locale.SIMPLIFIED_CHINESE);
        System.out.println(message);
        message = resourceBundleMessageSource.getMessage("btn.cancel", new Object[]{"昆仑"}, Locale.SIMPLIFIED_CHINESE);
        System.out.println(message);

    }
}
