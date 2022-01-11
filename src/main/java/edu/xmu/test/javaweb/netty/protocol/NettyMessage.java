package edu.xmu.test.javaweb.netty.protocol;

/**
 * <pre>
 * Netty私有协议, 协议格式如下:
 * Header: 消息头
 * crcCode, int, 32位:
 * - 0xABEF 固定幻数, 两个字节
 * - 主版本号, 一个字节
 * - 次版本号, 一个字节
 * Length, int, 32位, 消息长度, 包括消息头与消息体
 * SessionId, long, 64位, 会话ID
 * Type, byte, 8位, 消息类型
 * Priority, byte, 8位, 消息优先级
 * Attachment, Map<String, Object>, 变长, 可选字段, 用于扩展消息头
 * <p>
 * Body: 消息体; body编解码, 使用JSON将body序列化为byte数组
 * </pre>
 */
public class NettyMessage {
    Header header;
    Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
