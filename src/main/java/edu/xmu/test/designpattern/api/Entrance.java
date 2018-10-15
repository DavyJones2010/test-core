package edu.xmu.test.designpattern.api;

import com.alibaba.fastjson.JSON;

public class Entrance {
    public static void main(String[] args) {
        CommonClient client = new CommonClient();
        SpecificRequest request = new SpecificRequest();
        SpecificResponse specificResponse = client.doRequest(request);
        System.out.println(JSON.toJSONString(specificResponse));

        AnotherSpecificRequest request1 = new AnotherSpecificRequest();
        AnotherSpecificResponse anotherSpecificResponse = client.doRequest(request1);
        System.out.println(JSON.toJSONString(anotherSpecificResponse));
    }
}
