package edu.xmu.test.framework.spring.messagesource;

import java.util.ListResourceBundle;

/**
 * JDK中基本的ResourceBundle样例
 * Created by davywalker on 16/9/29.
 */
public class MyResources extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {
                        "OkKey", "OK"
                },
                {
                        "CancelKey", "Cancel"
                }};
    }
}
