package edu.xmu.test.designpattern.api;

public class AnotherSpecificRequest implements CommonRequest<AnotherSpecificResponse> {
    @Override
    public Class<AnotherSpecificResponse> getResponseClass() {
        return AnotherSpecificResponse.class;
    }
}
