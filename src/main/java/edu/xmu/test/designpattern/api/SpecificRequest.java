package edu.xmu.test.designpattern.api;

public class SpecificRequest implements CommonRequest<SpecificResponse> {
    @Override
    public Class<SpecificResponse> getResponseClass() {
        return SpecificResponse.class;
    }
}
