package edu.xmu.test.javaweb.netty;

import java.io.Serializable;

public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = 1950571328893167912L;
    Integer subReqId;
    String userName;
    String productName;
    String phoneNumber;
    String address;

    @Override
    public String toString() {
        return "SubscribeReq{" +
                "subReqId=" + subReqId +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
