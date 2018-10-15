package edu.xmu.test.designpattern.api;

import java.lang.reflect.Constructor;

public final class CommonClient {
    public <T extends CommonResponse> T doRequest(CommonRequest<T> request) {
        T t = null;
        try {
            Constructor<T> constructor = request.getResponseClass().getConstructor();
            t = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
