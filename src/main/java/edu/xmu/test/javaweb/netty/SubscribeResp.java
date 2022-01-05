package edu.xmu.test.javaweb.netty;

import java.io.Serializable;

public class SubscribeResp implements Serializable {
    private static final long serialVersionUID = -5221829837568432438L;
    Integer subReqId;
    Integer respCode;
    String desc;

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "subReqId=" + subReqId +
                ", respCode=" + respCode +
                ", desc='" + desc + '\'' +
                '}';
    }
}
