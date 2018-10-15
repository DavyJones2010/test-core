package edu.xmu.test.framework.spring.messagesource;

import java.util.ListResourceBundle;

/**
 * Created by davywalker on 16/9/29.
 */
public class MyResources_zh_CN extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {
                        "OkKey", "确定"
                },
                {
                        "CancelKey", "取消"
                }};
    }
}
